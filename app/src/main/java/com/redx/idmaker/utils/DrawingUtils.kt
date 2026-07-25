package com.redx.idmaker.utils

import android.graphics.*
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import kotlin.math.*

object DrawingUtils {

    /** Factory: create a paint with common options */
    fun paint(
        color: Int = Color.BLACK,
        textSize: Float = 28f,
        bold: Boolean = false,
        align: Paint.Align = Paint.Align.LEFT,
        alpha: Int = 255,
        antialias: Boolean = true
    ) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.color = color
        this.textSize = textSize
        this.typeface = if (bold) Typeface.DEFAULT_BOLD else Typeface.DEFAULT
        this.textAlign = align
        this.alpha = alpha
        this.isAntiAlias = antialias
    }

    /** Draw repeating security micro-text pattern */
    fun securityPattern(canvas: Canvas, w: Int, h: Int, text: String = "REDX", alpha: Int = 18) {
        val p = paint(Color.argb(alpha, 0, 80, 40), 14f)
        p.textAlign = Paint.Align.LEFT
        var y = 20f
        var toggle = false
        while (y < h) {
            var x = if (toggle) -30f else 0f
            while (x < w + 30) {
                canvas.drawText(text, x, y, p)
                x += p.measureText(text) + 12
            }
            y += 18f
            toggle = !toggle
        }
    }

    /** Round-corner bitmap (for photos) */
    fun roundBitmap(src: Bitmap, radius: Float = 8f): Bitmap {
        val output = Bitmap.createBitmap(src.width, src.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        val rect = RectF(0f, 0f, src.width.toFloat(), src.height.toFloat())
        canvas.drawRoundRect(rect, radius, radius, paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(src, 0f, 0f, paint)
        return output
    }

    /** Scale and center-crop a photo to fit target dimensions */
    fun scaledPhoto(src: Bitmap, targetW: Int, targetH: Int): Bitmap {
        val srcRatio = src.width.toFloat() / src.height
        val dstRatio = targetW.toFloat() / targetH
        val (sw, sh, sx, sy) = if (srcRatio > dstRatio) {
            val w = (src.height * dstRatio).toInt()
            listOf(w, src.height, (src.width - w) / 2, 0)
        } else {
            val h = (src.width / dstRatio).toInt()
            listOf(src.width, h, 0, (src.height - h) / 2)
        }
        val cropped = Bitmap.createBitmap(src, sx, sy, sw, sh)
        return Bitmap.createScaledBitmap(cropped, targetW, targetH, true)
    }

    /** Generate a QR code bitmap */
    fun generateQRCode(content: String, size: Int = 200): Bitmap {
        return try {
            val hints = mapOf(EncodeHintType.MARGIN to 1)
            val matrix = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size, hints)
            val bmp = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)
            for (x in 0 until size) for (y in 0 until size)
                bmp.setPixel(x, y, if (matrix[x, y]) Color.BLACK else Color.WHITE)
            bmp
        } catch (e: Exception) {
            Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also { Canvas(it).drawColor(Color.WHITE) }
        }
    }

    /** Generate ICAO 9303 MRZ – 2-line TD3 (passport) */
    fun mrz(
        type: String = "P",
        countryCode: String = "NGA",
        surname: String = "OKAFOR",
        given: String = "EMEKA",
        number: String = "A12345678",
        nationality: String = "NGA",
        dob: String = "900101",
        sex: String = "M",
        expiry: String = "300101"
    ): Pair<String, String> {
        fun pad(s: String, len: Int) = s.uppercase().replace(" ", "<").take(len).padEnd(len, '<')
        fun check(s: String): Int {
            val weights = intArrayOf(7, 3, 1)
            return s.sumOf { c ->
                val v = when {
                    c == '<' -> 0
                    c.isDigit() -> c.digitToInt()
                    c.isLetter() -> c.code - 'A'.code + 10
                    else -> 0
                }
                v * weights[(s.indexOf(c)) % 3]
            } % 10
        }
        val surnameField = pad(surname, 39 - given.length - 2).trimEnd('<')
        val line1 = "${pad(type, 1)}<${pad(countryCode, 3)}${pad(surname, 39 - given.replace(" ","<").length - 2)}" +
                "<<${pad(given, 39).let { it.take(39 - surnameField.length - 2) }}".take(44)
        val l1 = (type[0] + "<" + countryCode.take(3).uppercase().padEnd(3, '<') +
                surname.uppercase().replace(" ", "<") + "<<" +
                given.uppercase().replace(" ", "<")).padEnd(44, '<').take(44)
        val numPad = pad(number.replace(" ",""), 9)
        val dobPad = pad(dob, 6)
        val expPad = pad(expiry, 6)
        val natPad = pad(nationality, 3)
        val l2 = numPad + check(numPad).toString() + natPad + dobPad +
                check(dobPad).toString() + sex.take(1).uppercase() +
                expPad + check(expPad).toString() + "<<<<<<<<<<<<<<" + "0"
        return Pair(l1, l2.take(44))
    }

    /** Draw a fingerprint-style arc pattern */
    fun fingerprint(canvas: Canvas, cx: Float, cy: Float, maxR: Float, color: Int, alpha: Int = 60) {
        val p = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.color = color
            this.alpha = alpha
            style = Paint.Style.STROKE
            strokeWidth = 1.5f
        }
        var r = 6f
        while (r < maxR) {
            canvas.drawCircle(cx, cy, r, p)
            r += 5f
        }
    }

    /** Draw a label+value pair vertically */
    fun labelAndValue(
        canvas: Canvas, label: String, value: String,
        x: Float, y: Float, labelColor: Int, valueColor: Int,
        labelSize: Float = 22f, valueSize: Float = 30f
    ) {
        canvas.drawText(label.uppercase(), x, y, paint(labelColor, labelSize))
        canvas.drawText(value.ifBlank { "—" }, x, y + valueSize + 4, paint(valueColor, valueSize, bold = true))
    }

    /** Draw a thick ruled line */
    fun hline(canvas: Canvas, x1: Float, y: Float, x2: Float, color: Int, strokeWidth: Float = 2f) {
        canvas.drawLine(x1, y, x2, y, Paint(Paint.ANTI_ALIAS_FLAG).apply {
            this.color = color; this.strokeWidth = strokeWidth
        })
    }

    /** Draw a simple coat-of-arms placeholder circle with initials */
    fun emblem(canvas: Canvas, cx: Float, cy: Float, radius: Float, bgColor: Int, fgColor: Int, text: String) {
        val p = Paint(Paint.ANTI_ALIAS_FLAG)
        p.color = bgColor
        canvas.drawCircle(cx, cy, radius, p)
        p.color = fgColor
        p.style = Paint.Style.STROKE
        p.strokeWidth = 3f
        canvas.drawCircle(cx, cy, radius - 4, p)
        canvas.drawText(text, cx, cy + radius * 0.3f,
            paint(fgColor, radius * 0.55f, bold = true, align = Paint.Align.CENTER))
    }

    /** Star polygon helper */
    fun drawStar(canvas: Canvas, cx: Float, cy: Float, outerR: Float, innerR: Float, points: Int, paint: Paint) {
        val path = Path()
        val step = Math.PI / points
        for (i in 0 until points * 2) {
            val r = if (i % 2 == 0) outerR else innerR
            val angle = i * step - Math.PI / 2
            val px = (cx + r * cos(angle)).toFloat()
            val py = (cy + r * sin(angle)).toFloat()
            if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
        }
        path.close()
        canvas.drawPath(path, paint)
    }

    /** Wrap text to multiple lines within maxWidth */
    fun wrapText(canvas: Canvas, text: String, x: Float, startY: Float, maxWidth: Float, paint: Paint, lineHeight: Float): Float {
        val words = text.split(" ")
        var line = ""
        var y = startY
        for (word in words) {
            val test = if (line.isEmpty()) word else "$line $word"
            if (paint.measureText(test) <= maxWidth) {
                line = test
            } else {
                canvas.drawText(line, x, y, paint)
                y += lineHeight
                line = word
            }
        }
        if (line.isNotEmpty()) {
            canvas.drawText(line, x, y, paint)
            y += lineHeight
        }
        return y
    }
}
