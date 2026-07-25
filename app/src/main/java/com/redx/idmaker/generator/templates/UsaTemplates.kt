package com.redx.idmaker.generator.templates

import android.graphics.*
import com.redx.idmaker.generator.IdCardData
import com.redx.idmaker.utils.DrawingUtils
import kotlin.math.*

object UsaTemplates {

    private const val W = 1012
    private const val H = 638

    fun drawDriversLicense(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields
        val state = f["state"] ?: "Kansas"

        // State-colour scheme
        val (primaryColor, accentColor) = when (state.lowercase()) {
            "california" -> Pair("#003366", "#FFCC00")
            "texas"      -> Pair("#003087", "#BF0000")
            "new york"   -> Pair("#001F5B", "#F7941D")
            "florida"    -> Pair("#003087", "#FF6600")
            else         -> Pair("#002D62", "#E8B800")   // Kansas default: navy/gold
        }

        // Background
        val grad = LinearGradient(0f, 0f, 0f, H.toFloat(),
            Color.parseColor("#F5F8FF"), Color.parseColor("#DCE8FF"), Shader.TileMode.CLAMP)
        c.drawRect(0f, 0f, W.toFloat(), H.toFloat(), Paint().apply { shader = grad })
        DrawingUtils.securityPattern(c, W, H, "DL • ${state.uppercase()}", 14)

        // Left colour band
        c.drawRect(0f, 0f, 22f, H.toFloat(), Paint().apply { color = Color.parseColor(primaryColor) })

        // Top header
        c.drawRect(22f, 0f, W.toFloat(), 85f, Paint().apply { color = Color.parseColor(primaryColor) })

        // State name – large
        c.drawText(state.uppercase(), W / 2f, 55f,
            DrawingUtils.paint(Color.parseColor(accentColor), 46f, bold = true, align = Paint.Align.CENTER))
        c.drawText("DRIVER LICENSE", W / 2f, 82f,
            DrawingUtils.paint(Color.WHITE, 22f, align = Paint.Align.CENTER))

        // Gold star (REAL ID)
        DrawingUtils.drawStar(c, W - 50f, 44f, 28f, 12f, 5,
            Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor(accentColor) })
        c.drawText("★ REAL ID", W - 80f, 80f,
            DrawingUtils.paint(Color.parseColor(accentColor), 16f, bold = true))

        // Skyline silhouette (buildings)
        drawSkyline(c, primaryColor)

        // Photo (main)
        val px = 30f; val py = 100f; val pw = 165; val ph = 215
        drawPhoto(c, data.photo, px, py, pw, ph)

        // Small secondary photo (ghost/security)
        if (data.photo != null) {
            val ghost = DrawingUtils.scaledPhoto(data.photo, 65, 82)
            val ghostPaint = Paint().apply { alpha = 120 }
            c.drawBitmap(ghost, W - 110f, H - 110f, ghostPaint)
            c.drawRect(W - 110f, H - 110f, W - 45f, H - 28f, Paint(Paint.ANTI_ALIAS_FLAG).apply {
                color = Color.parseColor(primaryColor); style = Paint.Style.STROKE; strokeWidth = 2f
            })
        }

        // Fields
        val lc = Color.parseColor("#334466")
        val vc = Color.parseColor("#001133")
        var y = 100f; val x = 220f
        fun lv(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x, y, lc, vc, 16f, 26f)
            y += 52f
        }
        lv("LN", f["surname"]?.uppercase() ?: "")
        lv("FN", "${f["firstname"] ?: ""} ${f["middlename"] ?: ""}".trim())
        lv("DOB", f["dob"] ?: "")
        lv("ISS", f["issueDate"] ?: "")
        lv("EXP", f["expiryDate"] ?: "")

        var y2 = 100f; val x2 = 560f
        fun lv2(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x2, y2, lc, vc, 16f, 26f)
            y2 += 52f
        }
        lv2("DL NO.", f["licenceNo"] ?: "")
        lv2("CLASS", f["class"] ?: "C")
        lv2("HGT", f["height"] ?: "5-10")
        lv2("EYES", f["eyeColor"] ?: "BRN")
        lv2("SEX", f["gender"]?.take(1) ?: "M")

        // Donor indicator
        val donor = f["donor"] ?: "No"
        if (donor.equals("Yes", ignoreCase = true)) {
            val donorPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#CC0000") }
            c.drawRoundRect(x2, 370f, x2 + 180f, 405f, 6f, 6f, donorPaint)
            c.drawText("❤ DONOR", x2 + 90f, 395f,
                DrawingUtils.paint(Color.WHITE, 20f, bold = true, align = Paint.Align.CENTER))
        }

        // Address
        c.drawText("ADD", x, y, DrawingUtils.paint(lc, 16f))
        c.drawText((f["address"] ?: "").take(50), x, y + 28f,
            DrawingUtils.paint(vc, 22f, bold = true))

        // Bottom strip
        c.drawRect(22f, H - 55f, W.toFloat(), H.toFloat(), Paint().apply { color = Color.parseColor(primaryColor) })
        c.drawText("${state.uppercase()} DEPARTMENT OF REVENUE  •  MOTOR VEHICLE",
            W / 2f, H - 22f, DrawingUtils.paint(Color.WHITE, 18f, align = Paint.Align.CENTER))

        // Barcode stripe at very bottom
        val bc = Paint().apply { color = Color.BLACK }
        var bx = 22f
        while (bx < W - 5) {
            val bw = (3..6).random().toFloat()
            if ((bx / 8).toInt() % 2 == 0) c.drawRect(bx, H - 22f, bx + bw, H.toFloat(), bc)
            bx += bw + (1..3).random()
        }

        return bmp
    }

    private fun drawSkyline(c: Canvas, colorHex: String) {
        val p = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor(colorHex); alpha = 35
        }
        val buildings = listOf(
            Pair(700f to 200f, 60f to 300f),
            Pair(760f to 220f, 50f to 280f),
            Pair(810f to 180f, 80f to 320f),
            Pair(890f to 230f, 45f to 270f),
            Pair(935f to 210f, 65f to 290f)
        )
        for ((xRange, yRange) in buildings) {
            c.drawRect(xRange.first, yRange.first, xRange.first + xRange.second / 2, yRange.second, p)
        }
    }

    private fun drawPhoto(c: Canvas, photo: Bitmap?, px: Float, py: Float, pw: Int, ph: Int) {
        if (photo != null) {
            val scaled = DrawingUtils.scaledPhoto(photo, pw, ph)
            c.drawBitmap(DrawingUtils.roundBitmap(scaled, 6f), px, py, null)
        } else {
            c.drawRect(px, py, px + pw, py + ph, Paint().apply { color = Color.parseColor("#CCCCCC") })
            c.drawText("PHOTO", px + pw / 2, py + ph / 2,
                DrawingUtils.paint(Color.GRAY, 24f, align = Paint.Align.CENTER))
        }
        c.drawRect(px, py, px + pw, py + ph, Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#002D62"); style = Paint.Style.STROKE; strokeWidth = 3f
        })
    }
}
