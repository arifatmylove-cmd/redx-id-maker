package com.redx.idmaker.generator.templates

import android.graphics.*
import com.redx.idmaker.generator.IdCardData
import com.redx.idmaker.utils.DrawingUtils

object SouthAfricaTemplates {

    private const val W = 1012
    private const val H = 638

    fun drawSmartID(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        // Background: white with light texture
        c.drawColor(Color.WHITE)
        DrawingUtils.securityPattern(c, W, H, "RSA SMART ID", 14)

        // SA flag stripe at top
        drawSAFlagStripe(c, 0f, 0f, W.toFloat(), 22f)

        // Green header
        c.drawRect(0f, 22f, W.toFloat(), 100f, Paint().apply { color = Color.parseColor("#007749") })
        c.drawText("REPUBLIC OF SOUTH AFRICA", W / 2f, 54f,
            DrawingUtils.paint(Color.WHITE, 24f, bold = true, align = Paint.Align.CENTER))
        c.drawText("IDENTITY CARD  •  IDENTITEITSKAART", W / 2f, 88f,
            DrawingUtils.paint(Color.parseColor("#FFD700"), 20f, align = Paint.Align.CENTER))

        // SA flag stripe at bottom of header
        drawSAFlagStripe(c, 0f, 100f, W.toFloat(), 118f)

        // RSA coat of arms (simplified)
        DrawingUtils.emblem(c, 75f, 200f, 55f, Color.parseColor("#003082"), Color.parseColor("#FFD700"), "RSA")

        // Photo
        val px = 30f; val py = 130f; val pw = 175; val ph = 215
        if (data.photo != null) {
            c.drawBitmap(DrawingUtils.roundBitmap(DrawingUtils.scaledPhoto(data.photo, pw, ph), 6f), px, py, null)
        } else {
            c.drawRect(px, py, px + pw, py + ph, Paint().apply { color = Color.parseColor("#DDDDDD") })
            c.drawText("PHOTO", px + pw / 2, py + ph / 2,
                DrawingUtils.paint(Color.GRAY, 24f, align = Paint.Align.CENTER))
        }
        c.drawRect(px, py, px + pw, py + ph, Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#007749"); style = Paint.Style.STROKE; strokeWidth = 3f
        })

        // ID Number
        val idNo = f["idNumber"] ?: "0000000000000"
        c.drawText(idNo, W / 2f, 150f,
            DrawingUtils.paint(Color.parseColor("#003082"), 36f, bold = true, align = Paint.Align.CENTER))
        c.drawText("IDENTITY NUMBER / IDENTITEITSNOMMER", W / 2f, 174f,
            DrawingUtils.paint(Color.parseColor("#555555"), 18f, align = Paint.Align.CENTER))

        // Fields
        val lc = Color.parseColor("#003082"); val vc = Color.BLACK
        var y = 200f; val x = 230f
        fun lv(label: String, value: String, sub: String = "") {
            c.drawText(label, x, y, DrawingUtils.paint(lc, 16f))
            if (sub.isNotEmpty()) c.drawText("/ $sub", x + DrawingUtils.paint(lc, 16f).measureText(label) + 6, y,
                DrawingUtils.paint(Color.parseColor("#888888"), 14f))
            c.drawText(value.ifBlank { "—" }, x, y + 28f, DrawingUtils.paint(vc, 26f, bold = true))
            y += 56f
        }
        lv("SURNAME", f["surname"]?.uppercase() ?: "", "VAN")
        lv("NAMES / NAME", "${f["firstname"] ?: ""}", "VOORNAME")
        lv("NATIONALITY / NASIONALITEIT", f["nationality"] ?: "South African")
        lv("COUNTRY OF BIRTH / GEBOORTELAND", f["countryOfBirth"] ?: "South Africa")

        var y2 = 200f; val x2 = 600f
        fun lv2(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x2, y2, lc, vc, 16f, 26f)
            y2 += 56f
        }
        lv2("DATE OF BIRTH", f["dob"] ?: "")
        lv2("SEX / GESLAG", f["gender"]?.take(1) ?: "")
        lv2("CITIZENSHIP STATUS", f["citizenship"] ?: "Citizen")

        // Fingerprint
        DrawingUtils.fingerprint(c, 78f, H - 95f, 44f, Color.parseColor("#003082"), 50)

        // Chip
        val chipP = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#D4AF37") }
        c.drawRoundRect(40f, H - 155f, 120f, H - 110f, 8f, 8f, chipP)
        val cg = Paint().apply { color = Color.parseColor("#B8860B"); strokeWidth = 1f }
        for (i in 1..4) c.drawLine(40f + i * 16, H - 155f, 40f + i * 16, H - 110f, cg)
        for (i in 1..3) c.drawLine(40f, H - 155f + i * 15, 120f, H - 155f + i * 15, cg)

        // Bottom
        drawSAFlagStripe(c, 0f, H - 22f, W.toFloat(), H.toFloat())
        c.drawRect(0f, H - 55f, W.toFloat(), H - 22f, Paint().apply { color = Color.parseColor("#007749") })
        c.drawText("DHA • NOT VALID UNLESS SIGNED • home-affairs.gov.za",
            W / 2f, H - 30f, DrawingUtils.paint(Color.WHITE, 18f, align = Paint.Align.CENTER))

        return bmp
    }

    fun drawDriversLicence(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        c.drawColor(Color.parseColor("#F8FAFF"))
        DrawingUtils.securityPattern(c, W, H, "DLTC RSA", 12)

        // Top
        c.drawRect(0f, 0f, W.toFloat(), 85f, Paint().apply { color = Color.parseColor("#003082") })
        drawSAFlagStripe(c, 0f, 85f, W.toFloat(), 103f)
        c.drawText("REPUBLIC OF SOUTH AFRICA", W / 2f, 38f,
            DrawingUtils.paint(Color.WHITE, 24f, bold = true, align = Paint.Align.CENTER))
        c.drawText("DRIVER'S LICENCE", W / 2f, 74f,
            DrawingUtils.paint(Color.parseColor("#FFD700"), 30f, bold = true, align = Paint.Align.CENTER))

        // Photo
        val px = 30f; val py = 115f; val pw = 170; val ph = 212
        if (data.photo != null) {
            c.drawBitmap(DrawingUtils.roundBitmap(DrawingUtils.scaledPhoto(data.photo, pw, ph), 6f), px, py, null)
        } else {
            c.drawRect(px, py, px + pw, py + ph, Paint().apply { color = Color.LTGRAY })
        }
        c.drawRect(px, py, px + pw, py + ph, Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#003082"); style = Paint.Style.STROKE; strokeWidth = 3f
        })

        val lc = Color.parseColor("#003082"); val vc = Color.BLACK
        var y = 120f; val x = 225f
        fun lv(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x, y, lc, vc, 16f, 26f)
            y += 52f
        }
        lv("SURNAME", f["surname"]?.uppercase() ?: "")
        lv("NAMES", f["firstname"] ?: "")
        lv("DATE OF BIRTH", f["dob"] ?: "")
        lv("GENDER", f["gender"] ?: "")
        lv("LICENCE NO.", f["licenceNo"] ?: "")

        var y2 = 120f; val x2 = 570f
        fun lv2(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x2, y2, lc, vc, 16f, 26f)
            y2 += 52f
        }
        lv2("ISSUE DATE", f["issueDate"] ?: "")
        lv2("EXPIRY DATE", f["expiryDate"] ?: "")
        lv2("CODE ALLOWED", f["codeAllowed"] ?: "B")
        lv2("PrDP CODE", f["prDpCode"] ?: "—")

        DrawingUtils.fingerprint(c, 80f, H - 90f, 40f, Color.parseColor("#003082"), 50)

        c.drawRect(0f, H - 50f, W.toFloat(), H.toFloat(), Paint().apply { color = Color.parseColor("#003082") })
        c.drawText("ISSUED BY DEPARTMENT OF TRANSPORT RSA • rtmc.org.za",
            W / 2f, H - 18f, DrawingUtils.paint(Color.WHITE, 18f, align = Paint.Align.CENTER))

        return bmp
    }

    private fun drawSAFlagStripe(c: Canvas, x1: Float, y1: Float, x2: Float, y2: Float) {
        val h = y2 - y1; val w = x2 - x1
        // SA flag simplified horizontal stripe
        c.drawRect(x1, y1, x2, y1 + h / 6, Paint().apply { color = Color.parseColor("#007749") })
        c.drawRect(x1, y1 + h / 6, x2, y1 + h * 2 / 6, Paint().apply { color = Color.WHITE })
        c.drawRect(x1, y1 + h * 2 / 6, x2, y1 + h * 4 / 6, Paint().apply { color = Color.parseColor("#FFB81C") })
        c.drawRect(x1, y1 + h * 4 / 6, x2, y1 + h * 5 / 6, Paint().apply { color = Color.WHITE })
        c.drawRect(x1, y1 + h * 5 / 6, x2, y2, Paint().apply { color = Color.parseColor("#E03C31") })
        // Diagonal Y green shape
        val gp = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#007749") }
        val yPath = Path().apply {
            moveTo(x1, y1); lineTo(x1 + w * 0.25f, y1 + h / 2); lineTo(x1, y2); close()
        }
        c.drawPath(yPath, gp)
        val gp2 = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.BLACK }
        val yPath2 = Path().apply {
            moveTo(x1, y1 + h * 0.2f); lineTo(x1 + w * 0.22f, y1 + h / 2); lineTo(x1, y2 - h * 0.2f); close()
        }
        c.drawPath(yPath2, gp2)
    }
}
