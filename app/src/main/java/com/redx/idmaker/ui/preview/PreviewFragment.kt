package com.redx.idmaker.ui.preview

import android.Manifest
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.redx.idmaker.R
import com.redx.idmaker.SharedViewModel
import com.redx.idmaker.databinding.FragmentPreviewBinding
import com.redx.idmaker.generator.IdCardData
import com.redx.idmaker.generator.IdCardGenerator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class PreviewFragment : Fragment() {
    private var _binding: FragmentPreviewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SharedViewModel by activityViewModels()

    private val requestPermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) saveToGallery() else Toast.makeText(requireContext(), "Storage permission denied", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentPreviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        binding.ivCard.visibility = View.GONE
        binding.btnSave.isEnabled = false

        generateCard()

        binding.btnSave.setOnClickListener {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                    requestPermission.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    return@setOnClickListener
                }
            }
            saveToGallery()
        }

        binding.btnNewId.setOnClickListener {
            findNavController().navigate(R.id.action_preview_to_home)
        }
    }

    private fun generateCard() {
        val idType = viewModel.selectedIdType.value ?: return
        val fields = viewModel.formFields.value ?: mutableMapOf()
        val photo = viewModel.photoBitmap.value

        viewLifecycleOwner.lifecycleScope.launch {
            val bitmap = withContext(Dispatchers.Default) {
                try {
                    IdCardGenerator.generate(
                        IdCardData(idTypeId = idType.id, photo = photo, fields = fields)
                    )
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }
            if (_binding == null) return@launch
            binding.progressBar.visibility = View.GONE
            if (bitmap != null) {
                viewModel.generatedCard.value = bitmap
                binding.ivCard.setImageBitmap(bitmap)
                binding.ivCard.visibility = View.VISIBLE
                binding.btnSave.isEnabled = true
            } else {
                Toast.makeText(requireContext(), "Failed to generate ID", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveToGallery() {
        val bitmap = viewModel.generatedCard.value ?: return
        viewLifecycleOwner.lifecycleScope.launch {
            val saved = withContext(Dispatchers.IO) {
                trySaveImage(bitmap)
            }
            if (saved) {
                Toast.makeText(requireContext(), "✅ ID saved to gallery!", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Failed to save image", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun trySaveImage(bitmap: Bitmap): Boolean {
        return try {
            val filename = "RedxID_${System.currentTimeMillis()}.jpg"
            val stream: OutputStream
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val resolver = requireContext().contentResolver
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                    put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                    put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/RedxIDMaker")
                }
                val uri: Uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)!!
                stream = resolver.openOutputStream(uri)!!
            } else {
                @Suppress("DEPRECATION")
                val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "RedxIDMaker")
                dir.mkdirs()
                val file = File(dir, filename)
                stream = FileOutputStream(file)
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 95, stream)
            stream.flush()
            stream.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
