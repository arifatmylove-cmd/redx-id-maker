package com.redx.idmaker.generator.templates

import android.graphics.*
import com.redx.idmaker.generator.IdCardData
import com.redx.idmaker.utils.DrawingUtils
import kotlin.math.*

object AustraliaTemplates {

    private const val W = 1012
    private const val H = 638

    fun drawDriversLicence(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields
        val state = f["state"] ?: "New South Wales"

        // State colour scheme
        val (primary, accent) = when (state.lowercase()) {
            "victoria"              -> Pair("#003087", "#FFD700")
            "queensland"            -> Pair("#8B0000", "#FFD700")
            "western australia"     -> Pair("#003087", "#FFD700")
            "south australia"       -> Pair("#CC0000", "#FFFFFF")
            "tasmania"              -> Pair("#003087", "#FFFFFF")
            else                    -> Pair("#003087", "#FFD700")   // NSW
        }

        c.drawColor(Color.parseColor("#F2F6FF"))
        DrawingUtils.securityPattern(c, W, H, "AUSTROADS ${state.take(3).uppercase()}", 12)

        // Top bar
        c.drawRect(0f, 0f, W.toFloat(), 88f, Paint().apply { color = Color.parseColor(primary) })
        c.drawText(state.uppercase(), W / 2f, 42f,
            DrawingUtils.paint(Color.parseColor(accent), 30f, bold = true, align = Paint.Align.CENTER))
        c.drawText("DRIVER LICENCE", W / 2f, 76f,
            DrawingUtils.paint(Color.WHITE, 24f, align = Paint.Align.CENTER))

        // Southern Cross stars (Australian flag motif)
        drawSouthernCross(c, W - 140f, 44f, 40f, Color.WHITE)

        // Left blue stripe
        c.drawRect(0f, 88f, 16f, H.toFloat(), Paint().apply { color = Color.parseColor(primary) })

        // Photo
        val px = 30f; val py = 102f; val pw = 168; val ph = 210
        if (data.photo != null) {
            c.drawBitmap(DrawingUtils.roundBitmap(DrawingUtils.scaledPhoto(data.photo, pw, ph), 6f), px, py, null)
        } else {
            c.drawRect(px, py, px + pw, py + ph, Paint().apply { color = Color.LTGRAY })
            c.drawText("PHOTO", px + pw / 2, py + ph / 2,
                DrawingUtils.paint(Color.GRAY, 24f, align = Paint.Align.CENTER))
        }
        c.drawRect(px, py, px + pw, py + ph, Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor(primary); style = Paint.Style.STROKE; strokeWidth = 3f
        })

        // State seal
        DrawingUtils.emblem(c, 116f, H - 90f, 44f, Color.parseColor(primary), Color.WHITE, state.take(2).uppercase())

        val lc = Color.parseColor("#334466"); val vc = Color.BLACK
        var y = 108f; val x = 220f
        fun lv(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x, y, lc, vc, 16f, 26f)
            y += 52f
        }
        lv("FAMILY NAME", f["surname"]?.uppercase() ?: "")
        lv("GIVEN NAME(S)", f["firstname"] ?: "")
        lv("DATE OF BIRTH", f["dob"] ?: "")
        lv("SEX", f["gender"]?.take(1) ?: "")
        lv("HEIGHT", "${f["height"] ?: "170"} cm")

        var y2 = 108f; val x2 = 580f
        fun lv2(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x2, y2, lc, vc, 16f, 26f)
            y2 += 52f
        }
        lv2("LICENCE NO.", f["licenceNo"] ?: "")
        lv2("CLASS", f["licenceClass"] ?: "C")
        lv2("DATE LICENSED", f["issueDate"] ?: "")
        lv2("EXPIRY DATE", f["expiryDate"] ?: "")

        c.drawText("ADDRESS:", x, y, DrawingUtils.paint(lc, 16f))
        c.drawText((f["address"] ?: "").take(50), x, y + 28f, DrawingUtils.paint(vc, 22f, bold = true))

        // Bottom
        c.drawRect(16f, H - 52f, W.toFloat(), H.toFloat(), Paint().apply { color = Color.parseColor(primary) })
        c.drawText("AUSTRALIA  •  ${state.uppercase()}  •  transport.nsw.gov.au",
            W / 2f, H - 20f, DrawingUtils.paint(Color.WHITE, 18f, align = Paint.Align.CENTER))

        return bmp
    }

    private fun drawSouthernCross(canvas: Canvas, cx: Float, cy: Float, spread: Float, color: Int) {
        // 5 stars of the Southern Cross
        val positions = listOf(
            Pair(cx, cy - spread * 0.7f),             // top (Gamma)
            Pair(cx + spread, cy),                     // right (Alpha)
            Pair(cx + spread * 0.55f, cy + spread * 0.7f), // bottom-right (Beta)
            Pair(cx - spread * 0.3f, cy + spread * 0.6f),  // bottom-left (Delta)
            Pair(cx - spread * 0.6f, cy - spread * 0.1f)   // small (Epsilon)
        )
        val sizes = listOf(10f, 12f, 11f, 10f, 6f)
        val p = Paint(Paint.ANTI_ALIAS_FLAG).apply { this.color = color }
        positions.zip(sizes).forEach { (pos, size) ->
            DrawingUtils.drawStar(canvas, pos.first, pos.second, size, size * 0.45f, 5, p)
        }
    }
}
