package com.redx.idmaker.generator.templates

import android.graphics.*
import com.redx.idmaker.generator.IdCardData
import com.redx.idmaker.utils.DrawingUtils
import kotlin.math.*

object NigeriaTemplates {

    private const val W = 1012
    private const val H = 638

    // ── NIN SLIP ──────────────────────────────────────────────────────────────
    fun drawNinSlip(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        // Background
        c.drawColor(Color.WHITE)
        DrawingUtils.securityPattern(c, W, H, "NIN", 12)

        // Green top bar
        val greenBar = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#006600") }
        c.drawRect(0f, 0f, W.toFloat(), 90f, greenBar)

        // White stripe in green
        val whiteBar = Paint().apply { color = Color.WHITE }
        c.drawRect(0f, 30f, W.toFloat(), 60f, whiteBar)

        // Header text
        val hPaint = DrawingUtils.paint(Color.WHITE, 26f, bold = true, align = Paint.Align.CENTER)
        c.drawText("FEDERAL REPUBLIC OF NIGERIA", W / 2f, 26f, hPaint)
        c.drawText("NATIONAL IDENTITY MANAGEMENT COMMISSION", W / 2f, 82f, hPaint)
        hPaint.color = Color.parseColor("#006600")
        hPaint.textSize = 22f
        c.drawText("NATIONAL IDENTIFICATION NUMBER SLIP", W / 2f, 52f, hPaint)

        // Coat of arms placeholder
        drawCoatOfArms(c, 80f, 160f, 60f)

        // Photo
        val photoX = W - 220f; val photoY = 100f; val photoW = 160; val photoH = 200
        if (data.photo != null) {
            val scaled = DrawingUtils.scaledPhoto(data.photo, photoW, photoH)
            val rounded = DrawingUtils.roundBitmap(scaled, 8f)
            c.drawBitmap(rounded, photoX, photoY, null)
        } else {
            val ph = Paint().apply { color = Color.parseColor("#CCCCCC") }
            c.drawRect(photoX, photoY, photoX + photoW, photoY + photoH, ph)
            c.drawText("PHOTO", photoX + photoW / 2, photoY + photoH / 2,
                DrawingUtils.paint(Color.GRAY, 26f, align = Paint.Align.CENTER))
        }
        // Photo border
        val border = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#006600"); style = Paint.Style.STROKE; strokeWidth = 3f
        }
        c.drawRect(photoX, photoY, photoX + photoW, photoY + photoH, border)

        // NIN large display
        val nin = f["nin"] ?: "00000000000"
        val ninPaint = DrawingUtils.paint(Color.parseColor("#006600"), 56f, bold = true, align = Paint.Align.CENTER)
        c.drawText(nin.chunked(4).joinToString(" "), W / 2f - 80f, 175f, ninPaint)
        c.drawText("NATIONAL IDENTIFICATION NUMBER", W / 2f - 80f, 205f,
            DrawingUtils.paint(Color.DKGRAY, 20f, align = Paint.Align.CENTER))

        // Fields
        var y = 240f
        val labelColor = Color.parseColor("#555555")
        val valueColor = Color.BLACK
        fun field(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, 30f, y, labelColor, valueColor, 18f, 28f)
            y += 58f
        }
        field("SURNAME", f["surname"] ?: "")
        field("FIRST NAME", f["firstname"] ?: "")
        field("DATE OF BIRTH", f["dob"] ?: "")
        field("GENDER", f["gender"] ?: "")

        var y2 = 240f
        val x2 = 360f
        fun field2(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x2, y2, labelColor, valueColor, 18f, 28f)
            y2 += 58f
        }
        field2("PHONE NUMBER", f["phone"] ?: "")
        field2("ADDRESS", (f["address"] ?: "").take(35))

        // Bottom green bar
        val botBar = Paint().apply { color = Color.parseColor("#006600") }
        c.drawRect(0f, H - 60f, W.toFloat(), H.toFloat(), botBar)
        c.drawText("This slip is proof of NIN registration — it is NOT a valid identity card",
            W / 2f, H - 20f, DrawingUtils.paint(Color.WHITE, 18f, align = Paint.Align.CENTER))

        // NIMC watermark
        val wm = DrawingUtils.paint(Color.argb(25, 0, 100, 0), 100f, bold = true, align = Paint.Align.CENTER)
        c.save(); c.rotate(-30f, W / 2f, H / 2f)
        c.drawText("NIMC", W / 2f, H / 2f, wm)
        c.restore()

        return bmp
    }

    // ── NIMC CARD ─────────────────────────────────────────────────────────────
    fun drawNIMC(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        // Gradient background
        val grad = LinearGradient(0f, 0f, W.toFloat(), H.toFloat(),
            Color.parseColor("#003300"), Color.parseColor("#006633"), Shader.TileMode.CLAMP)
        c.drawRect(0f, 0f, W.toFloat(), H.toFloat(), Paint().apply { shader = grad })
        DrawingUtils.securityPattern(c, W, H, "NIMC", 20)

        // White content area
        val cardPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE }
        c.drawRoundRect(20f, 20f, (W - 20).toFloat(), (H - 20).toFloat(), 16f, 16f, cardPaint)

        // Top header band
        val headerPaint = Paint().apply { color = Color.parseColor("#006600") }
        c.drawRoundRect(20f, 20f, (W - 20).toFloat(), 105f, 16f, 16f, headerPaint)
        c.drawRect(20f, 89f, (W - 20).toFloat(), 105f, headerPaint)

        // Flag stripe: green-white-green
        val stripeH = 12f
        c.drawRect(20f, 100f, (W * 0.33f), 100f + stripeH, Paint().apply { color = Color.parseColor("#008000") })
        c.drawRect(W * 0.33f, 100f, (W * 0.66f), 100f + stripeH, Paint().apply { color = Color.WHITE })
        c.drawRect(W * 0.66f, 100f, (W - 20f), 100f + stripeH, Paint().apply { color = Color.parseColor("#008000") })

        c.drawText("FEDERAL REPUBLIC OF NIGERIA", W / 2f, 55f,
            DrawingUtils.paint(Color.WHITE, 26f, bold = true, align = Paint.Align.CENTER))
        c.drawText("NATIONAL IDENTITY CARD", W / 2f, 90f,
            DrawingUtils.paint(Color.WHITE, 22f, align = Paint.Align.CENTER))

        // Coat of arms
        drawCoatOfArms(c, 70f, 180f, 50f)

        // Photo
        val px = W - 210f; val py = 125f; val pw = 160; val ph = 190
        drawPhoto(c, data.photo, px, py, pw, ph)

        // NIN
        val nin = f["nin"] ?: "00000000000"
        c.drawText("NIN: ${nin.chunked(4).joinToString(" ")}", 30f, 165f,
            DrawingUtils.paint(Color.parseColor("#006600"), 28f, bold = true))

        // Fields grid
        var y = 200f
        fun lv(label: String, value: String, x: Float = 30f) {
            DrawingUtils.labelAndValue(c, label, value, x, y, Color.parseColor("#666666"), Color.parseColor("#111111"), 16f, 26f)
        }
        lv("SURNAME", f["surname"] ?: ""); lv("FIRST NAME", f["firstname"] ?: "", 360f); y += 52f
        lv("MIDDLE NAME", f["middlename"] ?: "—"); lv("GENDER", f["gender"] ?: "", 360f); y += 52f
        lv("DATE OF BIRTH", f["dob"] ?: ""); lv("NATIONALITY", f["nationality"] ?: "Nigerian", 360f)

        // Fingerprint
        DrawingUtils.fingerprint(c, 80f, 500f, 45f, Color.parseColor("#006600"), 50)

        // Chip rectangle
        val chipP = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#D4AF37"); style = Paint.Style.FILL
        }
        c.drawRoundRect(30f, 460f, 110f, 530f, 8f, 8f, chipP)
        val chipGrid = Paint().apply { color = Color.parseColor("#B8860B"); strokeWidth = 1f }
        for (i in 1..4) { c.drawLine(30f + i * 16, 460f, 30f + i * 16, 530f, chipGrid) }
        for (i in 1..4) { c.drawLine(30f, 460f + i * 14, 110f, 460f + i * 14, chipGrid) }

        // Bottom strip
        c.drawRect(20f, H - 55f, (W - 20).toFloat(), (H - 20).toFloat(),
            Paint().apply { color = Color.parseColor("#003300") })
        c.drawText("NOT VALID UNLESS SIGNED  •  REPORT LOSS/THEFT TO NIMC",
            W / 2f, H - 30f, DrawingUtils.paint(Color.parseColor("#AAFFAA"), 18f, align = Paint.Align.CENTER))

        return bmp
    }

    // ── DRIVER'S LICENCE ──────────────────────────────────────────────────────
    fun drawDriversLicence(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        // Background: white with subtle pattern
        c.drawColor(Color.parseColor("#F8F8F0"))
        DrawingUtils.securityPattern(c, W, H, "FRSC NIGERIA", 14)

        // Left blue stripe
        c.drawRect(0f, 0f, 18f, H.toFloat(), Paint().apply { color = Color.parseColor("#003087") })
        // Right blue stripe
        c.drawRect((W - 18).toFloat(), 0f, W.toFloat(), H.toFloat(), Paint().apply { color = Color.parseColor("#003087") })

        // Header
        c.drawRect(18f, 0f, (W - 18).toFloat(), 80f, Paint().apply { color = Color.parseColor("#003087") })
        c.drawText("FEDERAL REPUBLIC OF NIGERIA", W / 2f, 30f,
            DrawingUtils.paint(Color.WHITE, 22f, bold = true, align = Paint.Align.CENTER))
        c.drawText("DRIVER'S LICENCE", W / 2f, 60f,
            DrawingUtils.paint(Color.parseColor("#FFD700"), 28f, bold = true, align = Paint.Align.CENTER))

        // FRSC logo circle
        val frscPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#CC0000") }
        c.drawCircle(65f, 140f, 48f, frscPaint)
        c.drawText("FRSC", 65f, 153f,
            DrawingUtils.paint(Color.WHITE, 22f, bold = true, align = Paint.Align.CENTER))

        // Coat of arms
        drawCoatOfArms(c, 65f, 230f, 40f)

        // Nigerian flag stripe
        c.drawRect(18f, 80f, (W / 3).toFloat(), 98f, Paint().apply { color = Color.parseColor("#008000") })
        c.drawRect((W / 3).toFloat(), 80f, (W * 2 / 3).toFloat(), 98f, Paint().apply { color = Color.WHITE })
        c.drawRect((W * 2 / 3).toFloat(), 80f, (W - 18).toFloat(), 98f, Paint().apply { color = Color.parseColor("#008000") })

        // Photo
        val px = W - 220f; val py = 100f; val pw = 165; val ph = 205
        drawPhoto(c, data.photo, px, py, pw, ph)

        // QR Code
        val qrData = "LIC:${f["licenceNo"] ?: ""}|${f["surname"] ?: ""}|${f["firstname"] ?: ""}|${f["dob"] ?: ""}"
        val qr = DrawingUtils.generateQRCode(qrData, 100)
        c.drawBitmap(qr, W - 130f, H - 140f, null)

        // Fields
        val lc = Color.parseColor("#555555")
        val vc = Color.parseColor("#111111")
        var y = 110f
        fun lv(label: String, value: String, x: Float = 145f) {
            DrawingUtils.labelAndValue(c, label, value, x, y, lc, vc, 17f, 26f)
        }
        lv("SURNAME", f["surname"] ?: ""); y += 52f
        lv("FIRST NAME", f["firstname"] ?: ""); y += 52f
        lv("MIDDLE NAME", f["middlename"] ?: "—"); y += 52f
        lv("DATE OF BIRTH", f["dob"] ?: ""); y += 52f

        var y2 = 110f
        val x2 = 460f
        fun lv2(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x2, y2, lc, vc, 17f, 26f)
            y2 += 52f
        }
        lv2("LICENCE NO.", f["licenceNo"] ?: "")
        lv2("ISSUE DATE", f["issueDate"] ?: "")
        lv2("EXPIRY DATE", f["expiryDate"] ?: "")
        lv2("BLOOD GROUP", f["bloodGroup"] ?: "")

        // Address
        c.drawText("ADDRESS:", 145f, 330f, DrawingUtils.paint(lc, 17f))
        c.drawText((f["address"] ?: "").take(55), 145f, 360f, DrawingUtils.paint(vc, 24f, bold = true))

        // State seal
        val state = f["stateOfIssue"] ?: "LAGOS"
        c.drawText("STATE: $state", 145f, 400f, DrawingUtils.paint(Color.parseColor("#003087"), 22f, bold = true))

        // Bottom strip
        c.drawRect(18f, H - 55f, (W - 18).toFloat(), H.toFloat(), Paint().apply { color = Color.parseColor("#003087") })
        c.drawText("FRSC  •  ISSUED BY FEDERAL ROAD SAFETY COMMISSION  •  frsc.gov.ng",
            W / 2f, H - 22f, DrawingUtils.paint(Color.WHITE, 18f, align = Paint.Align.CENTER))

        return bmp
    }

    // ── PVC / VOTER'S CARD ────────────────────────────────────────────────────
    fun drawPVC(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        c.drawColor(Color.parseColor("#F5F0E8"))
        DrawingUtils.securityPattern(c, W, H, "INEC", 12)

        // Top bar: dark green
        c.drawRect(0f, 0f, W.toFloat(), 95f, Paint().apply { color = Color.parseColor("#1A5C1A") })

        // Nigerian flag in header
        drawNigeriaFlag(c, 30f, 8f, 80f, 40f)

        // INEC header text
        c.drawText("INDEPENDENT NATIONAL ELECTORAL COMMISSION", W / 2f, 40f,
            DrawingUtils.paint(Color.WHITE, 20f, bold = true, align = Paint.Align.CENTER))
        c.drawText("PERMANENT VOTER'S CARD", W / 2f, 72f,
            DrawingUtils.paint(Color.parseColor("#FFD700"), 28f, bold = true, align = Paint.Align.CENTER))

        // Nigeria map silhouette (simplified oval)
        drawNigeriaMap(c, 75f, 200f, 50f)

        // Photo
        val px = 30f; val py = 105f; val pw = 160; val ph = 200
        drawPhoto(c, data.photo, px, py, pw, ph)

        // VIN barcode area
        val vin = f["vin"] ?: "9DE4F01234567"
        c.drawText("VIN: $vin", 210f, 130f, DrawingUtils.paint(Color.parseColor("#1A5C1A"), 26f, bold = true))

        // Fields
        val lc = Color.parseColor("#555555")
        val vc = Color.BLACK
        var y = 165f
        val x = 210f
        fun lv(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x, y, lc, vc, 17f, 26f)
            y += 52f
        }
        lv("LAST NAME", f["surname"] ?: "")
        lv("FIRST NAME", f["firstname"] ?: "")
        lv("DATE OF BIRTH", f["dob"] ?: "")
        lv("GENDER", f["gender"] ?: "")

        var y2 = 165f
        val x2 = 560f
        fun lv2(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x2, y2, lc, vc, 17f, 26f)
            y2 += 52f
        }
        lv2("POLLING UNIT", f["pollingUnit"] ?: "")
        lv2("L.G.A.", f["lga"] ?: "")
        lv2("STATE", f["state"] ?: "")

        // Fingerprint zone
        DrawingUtils.fingerprint(c, 75f, 490f, 40f, Color.parseColor("#1A5C1A"), 45)
        c.drawText("RIGHT THUMB", 75f, 540f, DrawingUtils.paint(lc, 16f, align = Paint.Align.CENTER))

        // Bottom
        c.drawRect(0f, H - 50f, W.toFloat(), H.toFloat(), Paint().apply { color = Color.parseColor("#1A5C1A") })
        c.drawText("INEC • NOT TRANSFERABLE • inec.gov.ng",
            W / 2f, H - 18f, DrawingUtils.paint(Color.WHITE, 18f, align = Paint.Align.CENTER))

        return bmp
    }

    // ── HELPERS ───────────────────────────────────────────────────────────────
    fun drawCoatOfArms(canvas: Canvas, cx: Float, cy: Float, r: Float) {
        // Eagle-like emblem
        val gold = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#D4AF37") }
        val black = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.BLACK }
        val green = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#006600") }
        val white = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE }

        // Shield
        val shieldPath = Path().apply {
            moveTo(cx, cy - r)
            lineTo(cx + r * 0.7f, cy - r * 0.3f)
            lineTo(cx + r * 0.7f, cy + r * 0.3f)
            lineTo(cx, cy + r * 0.8f)
            lineTo(cx - r * 0.7f, cy + r * 0.3f)
            lineTo(cx - r * 0.7f, cy - r * 0.3f)
            close()
        }
        canvas.drawPath(shieldPath, black)

        // Green halves of shield
        val leftShield = Path().apply {
            moveTo(cx, cy - r)
            lineTo(cx, cy + r * 0.8f)
            lineTo(cx - r * 0.7f, cy + r * 0.3f)
            lineTo(cx - r * 0.7f, cy - r * 0.3f)
            close()
        }
        canvas.drawPath(leftShield, green)
        val rightShield = Path().apply {
            moveTo(cx, cy - r)
            lineTo(cx, cy + r * 0.8f)
            lineTo(cx + r * 0.7f, cy + r * 0.3f)
            lineTo(cx + r * 0.7f, cy - r * 0.3f)
            close()
        }
        canvas.drawPath(rightShield, green)

        // Eagle body (simple oval)
        canvas.drawOval(cx - r * 0.3f, cy - r * 1.4f, cx + r * 0.3f, cy - r * 0.85f, gold)
        // Wings
        val wingLeft = Path().apply {
            moveTo(cx - r * 0.3f, cy - r * 1.1f)
            lineTo(cx - r, cy - r * 1.4f)
            lineTo(cx - r * 0.8f, cy - r * 0.9f)
            close()
        }
        canvas.drawPath(wingLeft, gold)
        val wingRight = Path().apply {
            moveTo(cx + r * 0.3f, cy - r * 1.1f)
            lineTo(cx + r, cy - r * 1.4f)
            lineTo(cx + r * 0.8f, cy - r * 0.9f)
            close()
        }
        canvas.drawPath(wingRight, gold)

        // Horses (simplified triangles as supporters)
        canvas.drawText("🦅", cx, cy - r, DrawingUtils.paint(Color.TRANSPARENT, r))

        // Ribbon
        canvas.drawRect(cx - r * 0.8f, cy + r * 0.8f, cx + r * 0.8f, cy + r * 1.1f, gold)
        canvas.drawText("UNITY & FAITH", cx, cy + r,
            DrawingUtils.paint(Color.BLACK, r * 0.22f, bold = true, align = Paint.Align.CENTER))
    }

    fun drawNigeriaFlag(canvas: Canvas, x: Float, y: Float, w: Float, h: Float) {
        val g = Paint().apply { color = Color.parseColor("#008000") }
        val wh = Paint().apply { color = Color.WHITE }
        canvas.drawRect(x, y, x + w / 3, y + h, g)
        canvas.drawRect(x + w / 3, y, x + w * 2 / 3, y + h, wh)
        canvas.drawRect(x + w * 2 / 3, y, x + w, y + h, g)
    }

    fun drawNigeriaMap(canvas: Canvas, cx: Float, cy: Float, r: Float) {
        // Rough Nigeria outline as an irregular oval
        val p = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#006600"); alpha = 60
            style = Paint.Style.FILL
        }
        val path = Path().apply {
            moveTo(cx - r * 0.3f, cy - r)
            cubicTo(cx + r * 0.6f, cy - r * 0.9f, cx + r, cy - r * 0.2f, cx + r * 0.8f, cy + r * 0.5f)
            cubicTo(cx + r * 0.4f, cy + r, cx - r * 0.6f, cy + r, cx - r, cy + r * 0.3f)
            cubicTo(cx - r * 1.1f, cy - r * 0.3f, cx - r * 0.8f, cy - r * 0.7f, cx - r * 0.3f, cy - r)
            close()
        }
        canvas.drawPath(path, p)
    }

    private fun drawPhoto(c: Canvas, photo: android.graphics.Bitmap?, px: Float, py: Float, pw: Int, ph: Int) {
        if (photo != null) {
            val scaled = DrawingUtils.scaledPhoto(photo, pw, ph)
            val rounded = DrawingUtils.roundBitmap(scaled, 8f)
            c.drawBitmap(rounded, px, py, null)
        } else {
            c.drawRect(px, py, px + pw, py + ph, Paint().apply { color = Color.parseColor("#DDDDDD") })
            c.drawText("PHOTO", px + pw / 2, py + ph / 2,
                DrawingUtils.paint(Color.GRAY, 24f, align = Paint.Align.CENTER))
        }
        val border = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#003087"); style = Paint.Style.STROKE; strokeWidth = 3f
        }
        c.drawRect(px, py, px + pw, py + ph, border)
    }
}
