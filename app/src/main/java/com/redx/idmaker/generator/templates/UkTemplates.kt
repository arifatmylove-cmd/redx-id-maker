package com.redx.idmaker.generator.templates

import android.graphics.*
import com.redx.idmaker.generator.IdCardData
import com.redx.idmaker.utils.DrawingUtils

object UkTemplates {

    private const val W = 1012
    private const val H = 638

    // ── UK DRIVING LICENCE ────────────────────────────────────────────────────
    fun drawDrivingLicence(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        // Background: pale green/grey
        c.drawColor(Color.parseColor("#EEF5EE"))
        DrawingUtils.securityPattern(c, W, H, "DVLA UK", 12)

        // Green guilloche wave band at top
        val topBand = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#004D00") }
        c.drawRect(0f, 0f, W.toFloat(), 78f, topBand)

        // Union Jack in corner
        drawUnionJack(c, W - 110f, 8f, 100f, 60f)

        // Royal crest watermark
        drawRoyalCrest(c, W / 2f, H / 2f, 150f, Color.argb(18, 0, 60, 0))

        // Header
        c.drawText("UNITED KINGDOM", 30f, 32f,
            DrawingUtils.paint(Color.WHITE, 24f, bold = true))
        c.drawText("DRIVER'S LICENCE", 30f, 66f,
            DrawingUtils.paint(Color.parseColor("#FFD700"), 28f, bold = true))

        // Pink/salmon security band at bottom-right
        val pinkBand = Paint().apply { color = Color.parseColor("#FFB6C1") }
        c.drawRect(0f, H - 78f, W.toFloat(), H.toFloat(), pinkBand)

        // Photo
        val px = 30f; val py = 90f; val pw = 170; val ph = 215
        if (data.photo != null) {
            val scaled = DrawingUtils.scaledPhoto(data.photo, pw, ph)
            c.drawBitmap(DrawingUtils.roundBitmap(scaled, 6f), px, py, null)
        } else {
            c.drawRect(px, py, px + pw, py + ph, Paint().apply { color = Color.parseColor("#CCCCCC") })
            c.drawText("PHOTO", px + pw / 2, py + ph / 2,
                DrawingUtils.paint(Color.GRAY, 24f, align = Paint.Align.CENTER))
        }
        val photoBorder = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#004D00"); style = Paint.Style.STROKE; strokeWidth = 3f
        }
        c.drawRect(px, py, px + pw, py + ph, photoBorder)

        // Signature line
        c.drawLine(px, py + ph + 30f, px + pw, py + ph + 30f,
            Paint().apply { color = Color.parseColor("#004D00"); strokeWidth = 2f })
        c.drawText("Signature", px, py + ph + 26f,
            DrawingUtils.paint(Color.parseColor("#666666"), 16f))

        // Numbered fields (DVLA style)
        val lc = Color.parseColor("#003366")
        val vc = Color.BLACK
        var y = 96f
        val x = 225f
        fun field(num: String, label: String, value: String) {
            c.drawText("$num.", x, y, DrawingUtils.paint(lc, 16f, bold = true))
            c.drawText(label, x + 28f, y, DrawingUtils.paint(lc, 16f))
            c.drawText(value.ifBlank { "—" }, x + 28f, y + 24f,
                DrawingUtils.paint(vc, 26f, bold = true))
            y += 55f
        }

        val fullName = "${(f["surname"] ?: "").uppercase()}, ${f["firstname"] ?: ""}"
        field("1", "Surname", f["surname"]?.uppercase() ?: "")
        field("2", "Given Names", f["firstname"] ?: "")
        field("3", "Date of Birth", f["dob"] ?: "")
        field("4a", "Issue Date", f["issueDate"] ?: "")
        field("4b", "Expiry Date", f["expiryDate"] ?: "")

        var y2 = 96f
        val x2 = 580f
        fun field2(num: String, label: String, value: String) {
            c.drawText("$num.", x2, y2, DrawingUtils.paint(lc, 16f, bold = true))
            c.drawText(value.ifBlank { "—" }, x2 + 28f, y2 + 24f,
                DrawingUtils.paint(vc, 26f, bold = true))
            y2 += 55f
        }
        field2("4c", "Issuing Authority", "DVLA SWANSEA")
        field2("4d", "Licence Number", f["licenceNo"] ?: "")
        field2("5", "Address", (f["address"] ?: "").take(30))
        field2("7", "Height", "${f["height"] ?: "170"} cm")
        field2("8", "Place of Birth", f["placeOfBirth"] ?: "")

        // Bottom pink strip text
        c.drawText("DRIVING LICENCE  •  Not valid for hire & reward  •  Categories on reverse",
            W / 2f, H - 22f, DrawingUtils.paint(Color.parseColor("#333333"), 18f, align = Paint.Align.CENTER))

        // DVLA stamp
        DrawingUtils.emblem(c, W - 80f, H - 45f, 28f, Color.parseColor("#004D00"), Color.WHITE, "DVLA")

        return bmp
    }

    // ── BIOMETRIC RESIDENCE PERMIT ────────────────────────────────────────────
    fun drawResidencePermit(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        // Pink/salmon background
        c.drawColor(Color.parseColor("#FDE8EC"))
        DrawingUtils.securityPattern(c, W, H, "UK BRP HOME OFFICE", 14)

        // Top blue/navy bar
        val navy = Paint().apply { color = Color.parseColor("#00205B") }
        c.drawRect(0f, 0f, W.toFloat(), 88f, navy)

        // Union Jack top left
        drawUnionJack(c, 20f, 8f, 100f, 60f)

        // Title
        c.drawText("BIOMETRIC RESIDENCE PERMIT", W / 2f, 38f,
            DrawingUtils.paint(Color.WHITE, 24f, bold = true, align = Paint.Align.CENTER))
        c.drawText("HOME OFFICE • UNITED KINGDOM", W / 2f, 72f,
            DrawingUtils.paint(Color.parseColor("#FFD700"), 22f, align = Paint.Align.CENTER))

        // Permit ref number top right
        val refNo = f["permitNo"] ?: "BRP123456789"
        c.drawText(refNo, W - 30f, 30f,
            DrawingUtils.paint(Color.parseColor("#FFD700"), 18f, bold = true, align = Paint.Align.RIGHT))

        // Photo box
        val px = 30f; val py = 100f; val pw = 175; val ph = 220
        if (data.photo != null) {
            val scaled = DrawingUtils.scaledPhoto(data.photo, pw, ph)
            c.drawBitmap(DrawingUtils.roundBitmap(scaled, 6f), px, py, null)
        } else {
            c.drawRect(px, py, px + pw, py + ph, Paint().apply { color = Color.parseColor("#CCCCCC") })
            c.drawText("PHOTO", px + pw / 2, py + ph / 2,
                DrawingUtils.paint(Color.GRAY, 22f, align = Paint.Align.CENTER))
        }
        c.drawRect(px, py, px + pw, py + ph, Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#00205B"); style = Paint.Style.STROKE; strokeWidth = 3f
        })

        // Chip
        val chipP = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#D4AF37") }
        c.drawRoundRect(30f, 340f, 110f, 410f, 8f, 8f, chipP)
        val cGrid = Paint().apply { color = Color.parseColor("#B8860B"); strokeWidth = 1f }
        for (i in 1..4) c.drawLine(30f + i * 16, 340f, 30f + i * 16, 410f, cGrid)
        for (i in 1..4) c.drawLine(30f, 340f + i * 14, 110f, 340f + i * 14, cGrid)

        // Fields
        val lc = Color.parseColor("#00205B")
        val vc = Color.parseColor("#111111")
        var y = 106f; val x = 225f
        fun lv(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x, y, lc, vc, 17f, 27f)
            y += 56f
        }
        lv("SURNAME", f["surname"]?.uppercase() ?: "")
        lv("GIVEN NAMES", f["firstname"] ?: "")
        lv("DATE OF BIRTH", f["dob"] ?: "")
        lv("NATIONALITY", f["nationality"] ?: "")
        lv("GENDER", f["gender"] ?: "")

        var y2 = 106f; val x2 = 580f
        fun lv2(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x2, y2, lc, vc, 17f, 27f)
            y2 += 56f
        }
        lv2("PERMIT NO.", f["permitNo"] ?: "")
        lv2("ISSUE DATE", f["issueDate"] ?: "")
        lv2("EXPIRY DATE", f["expiryDate"] ?: "")
        lv2("IMMIGRATION STATUS", f["immigrationStatus"] ?: "")
        lv2("PLACE OF BIRTH", f["placeOfBirth"] ?: "")

        // UK logo watermark
        val wmPaint = DrawingUtils.paint(Color.argb(25, 0, 32, 91), 100f, bold = true, align = Paint.Align.CENTER)
        c.save(); c.rotate(-20f, W / 2f, H / 2f)
        c.drawText("UK", W / 2f, H / 2f, wmPaint)
        c.restore()

        // Bottom navy strip
        c.drawRect(0f, H - 60f, W.toFloat(), H.toFloat(), navy)
        c.drawText("NOT VALID FOR TRAVEL • THIS CARD IS THE PROPERTY OF THE HOME OFFICE",
            W / 2f, H - 22f, DrawingUtils.paint(Color.WHITE, 18f, align = Paint.Align.CENTER))

        return bmp
    }

    // ── HELPERS ───────────────────────────────────────────────────────────────
    private fun drawUnionJack(c: Canvas, x: Float, y: Float, w: Float, h: Float) {
        val blue = Paint().apply { color = Color.parseColor("#012169") }
        val red = Paint().apply { color = Color.parseColor("#C8102E") }
        val white = Paint().apply { color = Color.WHITE }
        // Blue field
        c.drawRect(x, y, x + w, y + h, blue)
        // White diagonals
        white.strokeWidth = h * 0.22f
        c.drawLine(x, y, x + w, y + h, white)
        c.drawLine(x + w, y, x, y + h, white)
        // Red diagonals (thinner)
        red.strokeWidth = h * 0.1f
        c.drawLine(x, y, x + w, y + h, red)
        c.drawLine(x + w, y, x, y + h, red)
        // White cross
        white.strokeWidth = 0f; white.style = Paint.Style.FILL
        c.drawRect(x, y + h * 0.38f, x + w, y + h * 0.62f, white)
        c.drawRect(x + w * 0.38f, y, x + w * 0.62f, y + h, white)
        // Red cross (thinner)
        c.drawRect(x, y + h * 0.44f, x + w, y + h * 0.56f, red)
        c.drawRect(x + w * 0.44f, y, x + w * 0.56f, y + h, red)
    }

    private fun drawRoyalCrest(c: Canvas, cx: Float, cy: Float, r: Float, color: Int) {
        val p = Paint(Paint.ANTI_ALIAS_FLAG).apply { this.color = color; style = Paint.Style.STROKE; strokeWidth = 3f }
        c.drawCircle(cx, cy, r, p)
        c.drawCircle(cx, cy, r * 0.6f, p)
        // Crown points
        for (i in 0..4) {
            val angle = Math.PI * i / 5 - Math.PI / 2
            val px = (cx + r * 0.8f * Math.cos(angle)).toFloat()
            val py = (cy + r * 0.8f * Math.sin(angle)).toFloat()
            c.drawLine(cx, cy, px, py, p)
        }
        p.style = Paint.Style.FILL
        p.color = color
        c.drawText("ER", cx, cy + r * 0.2f,
            DrawingUtils.paint(color, r * 0.35f, bold = true, align = Paint.Align.CENTER))
    }
}
