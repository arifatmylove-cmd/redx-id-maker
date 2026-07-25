package com.redx.idmaker.generator.templates

import android.graphics.*
import com.redx.idmaker.generator.IdCardData
import com.redx.idmaker.utils.DrawingUtils

object IndiaTemplates {

    private const val W = 1012
    private const val H = 638

    // ── AADHAAR CARD ──────────────────────────────────────────────────────────
    fun drawAadhaar(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        c.drawColor(Color.WHITE)
        DrawingUtils.securityPattern(c, W, H, "UIDAI INDIA", 12)

        // Top saffron bar
        c.drawRect(0f, 0f, W.toFloat(), 24f, Paint().apply { color = Color.parseColor("#FF9933") })
        // White bar
        c.drawRect(0f, 24f, W.toFloat(), 48f, Paint().apply { color = Color.WHITE })
        // Green bar
        c.drawRect(0f, 48f, W.toFloat(), 72f, Paint().apply { color = Color.parseColor("#138808") })

        // Ashok Chakra in flag stripe
        val chakraPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#000080"); style = Paint.Style.STROKE; strokeWidth = 2.5f
        }
        c.drawCircle(W / 2f, 36f, 16f, chakraPaint)
        for (i in 0 until 24) {
            val angle = Math.toRadians(i * 15.0)
            val x1 = (W / 2f + 6 * Math.cos(angle)).toFloat()
            val y1 = (36f + 6 * Math.sin(angle)).toFloat()
            val x2 = (W / 2f + 16 * Math.cos(angle)).toFloat()
            val y2 = (36f + 16 * Math.sin(angle)).toFloat()
            c.drawLine(x1, y1, x2, y2, chakraPaint)
        }

        // Header
        c.drawRect(0f, 72f, W.toFloat(), 155f, Paint().apply { color = Color.parseColor("#003580") })
        c.drawText("भारत सरकार  /  GOVERNMENT OF INDIA", W / 2f, 104f,
            DrawingUtils.paint(Color.WHITE, 22f, bold = true, align = Paint.Align.CENTER))
        c.drawText("UNIQUE IDENTIFICATION AUTHORITY OF INDIA  (UIDAI)", W / 2f, 134f,
            DrawingUtils.paint(Color.parseColor("#AADDFF"), 18f, align = Paint.Align.CENTER))

        // Aadhaar logo circle
        val logoPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#FF6600") }
        c.drawCircle(75f, 215f, 52f, logoPaint)
        c.drawText("आधार", 75f, 208f, DrawingUtils.paint(Color.WHITE, 24f, bold = true, align = Paint.Align.CENTER))
        c.drawText("Aadhaar", 75f, 236f, DrawingUtils.paint(Color.WHITE, 18f, align = Paint.Align.CENTER))

        // Photo
        val px = 30f; val py = 165f; val pw = 165; val ph = 205
        drawPhoto(c, data.photo, px + 100, py, pw, ph)

        // Fields
        val lc = Color.parseColor("#555555"); val vc = Color.BLACK
        var y = 175f; val x = 310f
        fun lv(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x, y, lc, vc, 16f, 26f)
            y += 52f
        }
        lv("NAME / नाम", "${f["firstname"] ?: ""} ${f["surname"] ?: ""}".trim())
        lv("DATE OF BIRTH / जन्म तिथि", f["dob"] ?: "")
        lv("GENDER / लिंग", f["gender"] ?: "")

        // Address
        c.drawText("ADDRESS / पता:", x, y, DrawingUtils.paint(lc, 16f))
        y += 22f
        val addr = f["address"] ?: ""
        DrawingUtils.wrapText(c, "$addr, PIN: ${f["pinCode"] ?: ""}", x, y, W - x - 30f,
            DrawingUtils.paint(vc, 22f, bold = true), 28f)

        // Big Aadhaar number
        val aadhaarNo = (f["aadhaarNo"] ?: "0000 0000 0000")
            .chunked(4).joinToString(" ").take(14)
        c.drawText(aadhaarNo, W / 2f, H - 75f,
            DrawingUtils.paint(Color.parseColor("#003580"), 52f, bold = true, align = Paint.Align.CENTER))
        c.drawText("Aadhaar Number", W / 2f, H - 40f,
            DrawingUtils.paint(Color.parseColor("#666666"), 20f, align = Paint.Align.CENTER))

        // Fingerprint
        DrawingUtils.fingerprint(c, W - 80f, H - 100f, 45f, Color.parseColor("#003580"), 55)

        // Bottom strip
        c.drawRect(0f, H - 22f, W.toFloat(), H.toFloat(), Paint().apply { color = Color.parseColor("#FF9933") })
        c.drawText("mAadhaar  •  uidai.gov.in  •  1947",
            W / 2f, H - 4f, DrawingUtils.paint(Color.WHITE, 16f, align = Paint.Align.CENTER))

        return bmp
    }

    // ── PAN CARD ──────────────────────────────────────────────────────────────
    fun drawPAN(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        // Cream background
        c.drawColor(Color.parseColor("#FEFDE8"))
        DrawingUtils.securityPattern(c, W, H, "INCOME TAX INDIA", 12)

        // Blue top header
        c.drawRect(0f, 0f, W.toFloat(), 100f, Paint().apply { color = Color.parseColor("#003580") })

        // Ashok Chakra (large, top center)
        val chakP = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#FFD700"); style = Paint.Style.STROKE; strokeWidth = 3f
        }
        c.drawCircle(75f, 50f, 36f, chakP)
        for (i in 0 until 24) {
            val angle = Math.toRadians(i * 15.0)
            val x1 = (75f + 12 * Math.cos(angle)).toFloat()
            val y1 = (50f + 12 * Math.sin(angle)).toFloat()
            val x2 = (75f + 35 * Math.cos(angle)).toFloat()
            val y2 = (50f + 35 * Math.sin(angle)).toFloat()
            c.drawLine(x1, y1, x2, y2, chakP)
        }

        c.drawText("INCOME TAX DEPARTMENT", W / 2f, 42f,
            DrawingUtils.paint(Color.WHITE, 26f, bold = true, align = Paint.Align.CENTER))
        c.drawText("GOVT. OF INDIA", W / 2f, 75f,
            DrawingUtils.paint(Color.parseColor("#FFD700"), 22f, align = Paint.Align.CENTER))

        // IT Dept logo on right
        DrawingUtils.emblem(c, W - 72f, 50f, 40f, Color.parseColor("#FFD700"), Color.parseColor("#003580"), "IT")

        // Photo
        val px = W - 210f; val py = 115f; val pw = 165; val ph = 200
        drawPhoto(c, data.photo, px, py, pw, ph)

        // PAN number – big gold
        val pan = (f["panNo"] ?: "ABCDE1234F").uppercase()
        c.drawText(pan, W / 2f - 60f, 152f,
            DrawingUtils.paint(Color.parseColor("#B8860B"), 52f, bold = true, align = Paint.Align.CENTER))
        DrawingUtils.hline(c, 30f, 165f, (W - 250f).toFloat(), Color.parseColor("#003580"), 2f)
        c.drawText("PERMANENT ACCOUNT NUMBER", W / 2f - 60f, 188f,
            DrawingUtils.paint(Color.parseColor("#003580"), 18f, align = Paint.Align.CENTER))

        // Fields
        val lc = Color.parseColor("#555555"); val vc = Color.parseColor("#111111")
        var y = 210f; val x = 30f
        fun lv(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x, y, lc, vc, 17f, 30f)
            y += 60f
        }
        lv("Name / नाम", "${f["firstname"] ?: ""} ${f["surname"] ?: ""}".trim())
        lv("Father's Name / पिता का नाम", f["fathersName"] ?: "")
        lv("Date of Birth / जन्म तिथि", f["dob"] ?: "")

        // Signature line
        DrawingUtils.hline(c, 30f, H - 90f, 260f, Color.parseColor("#003580"), 1.5f)
        c.drawText("Signature / हस्ताक्षर", 30f, H - 70f, DrawingUtils.paint(lc, 16f))

        // Bottom
        c.drawRect(0f, H - 50f, W.toFloat(), H.toFloat(), Paint().apply { color = Color.parseColor("#003580") })
        c.drawText("INCOME TAX DEPARTMENT  •  incometaxindia.gov.in",
            W / 2f, H - 18f, DrawingUtils.paint(Color.WHITE, 18f, align = Paint.Align.CENTER))

        return bmp
    }

    private fun drawPhoto(c: Canvas, photo: Bitmap?, px: Float, py: Float, pw: Int, ph: Int) {
        if (photo != null) {
            c.drawBitmap(DrawingUtils.roundBitmap(DrawingUtils.scaledPhoto(photo, pw, ph), 6f), px, py, null)
        } else {
            c.drawRect(px, py, px + pw, py + ph, Paint().apply { color = Color.parseColor("#DDDDDD") })
            c.drawText("PHOTO", px + pw / 2, py + ph / 2,
                DrawingUtils.paint(Color.GRAY, 24f, align = Paint.Align.CENTER))
        }
        c.drawRect(px, py, px + pw, py + ph, Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#003580"); style = Paint.Style.STROKE; strokeWidth = 3f
        })
    }
}
