package com.redx.idmaker.generator.templates

import android.graphics.*
import com.redx.idmaker.generator.IdCardData
import com.redx.idmaker.utils.DrawingUtils

object GhanaTemplates {

    private const val W = 1012
    private const val H = 638

    fun drawGhanaCard(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        // Background: dark blue
        val grad = LinearGradient(0f, 0f, W.toFloat(), H.toFloat(),
            Color.parseColor("#001A4E"), Color.parseColor("#003080"), Shader.TileMode.CLAMP)
        c.drawRect(0f, 0f, W.toFloat(), H.toFloat(), Paint().apply { shader = grad })
        DrawingUtils.securityPattern(c, W, H, "NIA GHANA", 22)

        // Gold top strip
        c.drawRect(0f, 0f, W.toFloat(), 8f, Paint().apply { color = Color.parseColor("#D4AF37") })
        c.drawRect(0f, H - 8f, W.toFloat(), H.toFloat(), Paint().apply { color = Color.parseColor("#D4AF37") })

        // Flag stripe: red-gold-green with black star
        val stripeY = 75f; val stripeH = 18f
        c.drawRect(0f, stripeY, (W / 3).toFloat(), stripeY + stripeH, Paint().apply { color = Color.parseColor("#CE1126") })
        c.drawRect((W / 3).toFloat(), stripeY, (W * 2 / 3).toFloat(), stripeY + stripeH, Paint().apply { color = Color.parseColor("#FCD116") })
        c.drawRect((W * 2 / 3).toFloat(), stripeY, W.toFloat(), stripeY + stripeH, Paint().apply { color = Color.parseColor("#006B3F") })
        // Black star in middle
        DrawingUtils.drawStar(c, W / 2f, stripeY + stripeH / 2, 12f, 5f, 5,
            Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.BLACK })

        // Header
        c.drawText("REPUBLIC OF GHANA", W / 2f, 40f,
            DrawingUtils.paint(Color.parseColor("#FFD700"), 26f, bold = true, align = Paint.Align.CENTER))
        c.drawText("GHANA CARD  •  NIA", W / 2f, 68f,
            DrawingUtils.paint(Color.WHITE, 22f, align = Paint.Align.CENTER))

        // NIA eagle emblem
        DrawingUtils.emblem(c, 75f, 160f, 52f, Color.parseColor("#D4AF37"), Color.parseColor("#001A4E"), "NIA")

        // Photo
        val px = 30f; val py = 104f; val pw = 165; val ph = 210
        drawPhoto(c, data.photo, px, py, pw, ph, Color.parseColor("#D4AF37"))

        // ID Number
        val idNo = f["idNumber"] ?: "GHA-000000000-0"
        c.drawText(idNo, W / 2f, 120f,
            DrawingUtils.paint(Color.parseColor("#FFD700"), 34f, bold = true, align = Paint.Align.CENTER))
        c.drawText("CARD NUMBER", W / 2f, 144f,
            DrawingUtils.paint(Color.parseColor("#AAAAAA"), 18f, align = Paint.Align.CENTER))

        // Fields
        val lc = Color.parseColor("#AACCFF")
        val vc = Color.WHITE
        var y = 165f; val x = 225f
        fun lv(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x, y, lc, vc, 16f, 26f)
            y += 52f
        }
        lv("SURNAME", f["surname"]?.uppercase() ?: "")
        lv("GIVEN NAMES", "${f["firstname"] ?: ""} ${f["middlename"] ?: ""}".trim())
        lv("DATE OF BIRTH", f["dob"] ?: "")
        lv("GENDER", f["gender"] ?: "")

        var y2 = 165f; val x2 = 570f
        fun lv2(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x2, y2, lc, vc, 16f, 26f)
            y2 += 52f
        }
        lv2("NATIONALITY", f["nationality"] ?: "Ghanaian")
        lv2("EXPIRY DATE", f["expiryDate"] ?: "")

        // Fingerprint
        DrawingUtils.fingerprint(c, W - 80f, H - 90f, 55f, Color.parseColor("#D4AF37"), 60)

        // Bottom
        c.drawText("THIS CARD IS THE PROPERTY OF THE NIA OF GHANA • NOT TRANSFERABLE",
            W / 2f, H - 22f, DrawingUtils.paint(Color.parseColor("#AAAAAA"), 16f, align = Paint.Align.CENTER))

        return bmp
    }

    fun drawVoterID(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        c.drawColor(Color.parseColor("#F5F5DC"))
        DrawingUtils.securityPattern(c, W, H, "EC GHANA", 12)

        // Top red/gold/green stripe
        val sh = 20f
        c.drawRect(0f, 0f, (W / 3).toFloat(), sh, Paint().apply { color = Color.parseColor("#CE1126") })
        c.drawRect((W / 3).toFloat(), 0f, (W * 2 / 3).toFloat(), sh, Paint().apply { color = Color.parseColor("#FCD116") })
        c.drawRect((W * 2 / 3).toFloat(), 0f, W.toFloat(), sh, Paint().apply { color = Color.parseColor("#006B3F") })

        // Header bar
        c.drawRect(0f, sh, W.toFloat(), sh + 70f, Paint().apply { color = Color.parseColor("#CE1126") })
        c.drawText("ELECTORAL COMMISSION OF GHANA", W / 2f, sh + 32f,
            DrawingUtils.paint(Color.WHITE, 22f, bold = true, align = Paint.Align.CENTER))
        c.drawText("VOTER IDENTIFICATION CARD", W / 2f, sh + 62f,
            DrawingUtils.paint(Color.parseColor("#FFD700"), 24f, bold = true, align = Paint.Align.CENTER))

        // Photo
        val px = 30f; val py = sh + 80f; val pw = 160; val ph = 200
        drawPhoto(c, data.photo, px, py, pw.toInt(), ph.toInt(), Color.parseColor("#CE1126"))

        // Voter No
        c.drawText(f["voterNo"] ?: "0000000000", 220f, sh + 110f,
            DrawingUtils.paint(Color.parseColor("#CE1126"), 36f, bold = true))
        c.drawText("VOTER ID NUMBER", 220f, sh + 136f,
            DrawingUtils.paint(Color.parseColor("#555555"), 18f))

        val lc = Color.parseColor("#555555"); val vc = Color.BLACK
        var y = sh + 160f; val x = 220f
        fun lv(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x, y, lc, vc, 16f, 26f)
            y += 52f
        }
        lv("SURNAME", f["surname"]?.uppercase() ?: "")
        lv("GIVEN NAMES", f["firstname"] ?: "")
        lv("DATE OF BIRTH", f["dob"] ?: "")
        lv("GENDER", f["gender"] ?: "")

        var y2 = sh + 160f; val x2 = 570f
        fun lv2(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x2, y2, lc, vc, 16f, 26f)
            y2 += 52f
        }
        lv2("REGION", f["region"] ?: "")
        lv2("CONSTITUENCY", f["constituency"] ?: "")

        DrawingUtils.fingerprint(c, 75f, H - 100f, 40f, Color.parseColor("#CE1126"), 50)

        c.drawRect(0f, H - 48f, W.toFloat(), H.toFloat(), Paint().apply { color = Color.parseColor("#CE1126") })
        c.drawText("NOT TRANSFERABLE  •  FOR USE IN GHANA ONLY",
            W / 2f, H - 18f, DrawingUtils.paint(Color.WHITE, 18f, align = Paint.Align.CENTER))

        return bmp
    }

    private fun drawPhoto(c: Canvas, photo: Bitmap?, px: Float, py: Float, pw: Int, ph: Int, borderColor: Int) {
        if (photo != null) {
            c.drawBitmap(DrawingUtils.roundBitmap(DrawingUtils.scaledPhoto(photo, pw, ph), 6f), px, py, null)
        } else {
            c.drawRect(px, py, px + pw, py + ph, Paint().apply { color = Color.parseColor("#CCCCCC") })
            c.drawText("PHOTO", px + pw / 2, py + ph / 2,
                DrawingUtils.paint(Color.GRAY, 24f, align = Paint.Align.CENTER))
        }
        c.drawRect(px, py, px + pw, py + ph, Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = borderColor; style = Paint.Style.STROKE; strokeWidth = 3f
        })
    }
}
