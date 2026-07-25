package com.redx.idmaker.ui.form

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.redx.idmaker.R
import com.redx.idmaker.SharedViewModel
import com.redx.idmaker.data.DocumentField
import com.redx.idmaker.data.FieldType
import com.redx.idmaker.data.IdCategory
import com.redx.idmaker.databinding.FragmentFormBinding
import java.text.SimpleDateFormat
import java.util.*

class FormFragment : Fragment() {
    private var _binding: FragmentFormBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by activityViewModels()
    private val fieldViews = mutableMapOf<String, View>()

    private val imagePicker = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { loadPhoto(it) }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val idType = viewModel.selectedIdType.value ?: return
        binding.tvFormTitle.text = idType.name

        // Show photo picker only for non-company docs
        val needsPhoto = idType.category != IdCategory.COMPANY
        binding.photoSection.visibility = if (needsPhoto) View.VISIBLE else View.GONE
        binding.ivPhoto.setOnClickListener { imagePicker.launch("image/*") }
        binding.btnPickPhoto.setOnClickListener { imagePicker.launch("image/*") }

        buildFields(idType.requiredFields)

        binding.btnGenerate.setOnClickListener {
            saveFields()
            if (needsPhoto && viewModel.photoBitmap.value == null) {
                Toast.makeText(requireContext(), "Please select a photo", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            findNavController().navigate(R.id.action_form_to_preview)
        }
    }

    private fun loadPhoto(uri: Uri) {
        try {
            val stream = requireContext().contentResolver.openInputStream(uri)
            val bitmap = android.graphics.BitmapFactory.decodeStream(stream)
            stream?.close()
            bitmap?.let {
                viewModel.photoBitmap.value = it
                binding.ivPhoto.setImageBitmap(it)
            }
        } catch (e: Exception) {
            Toast.makeText(requireContext(), "Failed to load image", Toast.LENGTH_SHORT).show()
        }
    }

    private fun buildFields(fields: List<DocumentField>) {
        val ctx = requireContext()
        fields.forEach { field ->
            when (field.type) {
                FieldType.TEXT -> {
                    val til = TextInputLayout(ctx).apply {
                        hint = field.label + if (!field.required) " (optional)" else ""
                        layoutParams = ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).apply { setMargins(0, 16, 0, 0) }
                    }
                    val et = TextInputEditText(til.context)
                    til.addView(et)
                    binding.fieldsContainer.addView(til)
                    fieldViews[field.key] = et
                }
                FieldType.DATE -> {
                    val til = TextInputLayout(ctx).apply {
                        hint = field.label
                        endIconMode = TextInputLayout.END_ICON_CUSTOM
                        setEndIconDrawable(R.drawable.ic_calendar)
                        layoutParams = ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).apply { setMargins(0, 16, 0, 0) }
                    }
                    val et = TextInputEditText(til.context).apply { isFocusable = false }
                    til.addView(et)
                    val clickAction = View.OnClickListener {
                        val cal = Calendar.getInstance()
                        DatePickerDialog(ctx, { _, y, m, d ->
                            et.setText("%02d/%02d/%d".format(d, m + 1, y))
                        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show()
                    }
                    et.setOnClickListener(clickAction)
                    til.setEndIconOnClickListener(clickAction)
                    binding.fieldsContainer.addView(til)
                    fieldViews[field.key] = et
                }
                FieldType.DROPDOWN -> {
                    val til = TextInputLayout(ctx, null, com.google.android.material.R.attr.textInputOutlinedExposedDropdownMenuStyle).apply {
                        hint = field.label
                        layoutParams = ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).apply { setMargins(0, 16, 0, 0) }
                    }
                    val autoComplete = AutoCompleteTextView(til.context).apply {
                        setAdapter(ArrayAdapter(ctx, android.R.layout.simple_dropdown_item_1line, field.options))
                        inputType = 0
                    }
                    til.addView(autoComplete)
                    binding.fieldsContainer.addView(til)
                    fieldViews[field.key] = autoComplete
                }
                FieldType.MULTILINE -> {
                    val til = TextInputLayout(ctx).apply {
                        hint = field.label
                        layoutParams = ViewGroup.MarginLayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                        ).apply { setMargins(0, 16, 0, 0) }
                    }
                    val et = TextInputEditText(til.context).apply {
                        minLines = 2
                        maxLines = 4
                        inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_FLAG_MULTI_LINE
                    }
                    til.addView(et)
                    binding.fieldsContainer.addView(til)
                    fieldViews[field.key] = et
                }
            }
        }
    }

    private fun saveFields() {
        fieldViews.forEach { (key, view) ->
            val value = when (view) {
                is TextInputEditText -> view.text.toString()
                is AutoCompleteTextView -> view.text.toString()
                else -> ""
            }
            viewModel.setField(key, value)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
