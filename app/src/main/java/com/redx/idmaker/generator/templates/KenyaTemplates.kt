package com.redx.idmaker.generator.templates

import android.graphics.*
import com.redx.idmaker.generator.IdCardData
import com.redx.idmaker.utils.DrawingUtils

object KenyaTemplates {

    private const val W = 1012
    private const val H = 638

    fun drawNationalID(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        // Background: cream/beige
        c.drawColor(Color.parseColor("#FAF3E0"))
        DrawingUtils.securityPattern(c, W, H, "KENYA NATIONAL ID", 12)

        // Kenya flag stripe: black-red-green with white borders
        val stripeY = 0f; val stripeH = 75f
        c.drawRect(0f, stripeY, W.toFloat(), stripeY + stripeH * 0.33f, Paint().apply { color = Color.BLACK })
        c.drawRect(0f, stripeY + stripeH * 0.33f, W.toFloat(), stripeY + stripeH * 0.4f, Paint().apply { color = Color.WHITE })
        c.drawRect(0f, stripeY + stripeH * 0.4f, W.toFloat(), stripeY + stripeH * 0.6f, Paint().apply { color = Color.parseColor("#BB0000") })
        c.drawRect(0f, stripeY + stripeH * 0.6f, W.toFloat(), stripeY + stripeH * 0.67f, Paint().apply { color = Color.WHITE })
        c.drawRect(0f, stripeY + stripeH * 0.67f, W.toFloat(), stripeY + stripeH, Paint().apply { color = Color.parseColor("#006600") })

        // Maasai shield (simplified) in flag
        val shieldP = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#BB0000") }
        val shieldPath = Path().apply {
            moveTo(W / 2f, stripeH * 0.1f)
            lineTo(W / 2f + 15, stripeH * 0.5f)
            lineTo(W / 2f, stripeH * 0.9f)
            lineTo(W / 2f - 15, stripeH * 0.5f)
            close()
        }
        val shieldWhite = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.WHITE }
        c.drawPath(shieldPath, shieldWhite)
        // Spears
        val spearP = Paint().apply { color = Color.parseColor("#888888"); strokeWidth = 3f }
        c.drawLine(W / 2f - 20, stripeY, W / 2f - 20, stripeY + stripeH, spearP)
        c.drawLine(W / 2f + 20, stripeY, W / 2f + 20, stripeY + stripeH, spearP)

        // Subtitle
        c.drawRect(0f, stripeH, W.toFloat(), stripeH + 40f, Paint().apply { color = Color.parseColor("#006600") })
        c.drawText("JAMHURI YA KENYA  •  REPUBLIC OF KENYA  •  NATIONAL IDENTITY CARD",
            W / 2f, stripeH + 28f, DrawingUtils.paint(Color.WHITE, 18f, align = Paint.Align.CENTER))

        // Photo (right side)
        val px = W - 210f; val py = stripeH + 48f; val pw = 175; val ph = 218
        if (data.photo != null) {
            c.drawBitmap(DrawingUtils.roundBitmap(DrawingUtils.scaledPhoto(data.photo, pw, ph), 6f), px, py, null)
        } else {
            c.drawRect(px, py, px + pw, py + ph, Paint().apply { color = Color.parseColor("#DDDDDD") })
            c.drawText("PHOTO", px + pw / 2, py + ph / 2,
                DrawingUtils.paint(Color.GRAY, 24f, align = Paint.Align.CENTER))
        }
        c.drawRect(px, py, px + pw, py + ph, Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#006600"); style = Paint.Style.STROKE; strokeWidth = 3f
        })

        // Kenya coat of arms (simplified circle)
        DrawingUtils.emblem(c, 78f, stripeH + 80f, 58f, Color.parseColor("#BB0000"), Color.WHITE, "KE")

        // ID Number
        val idNo = f["idNumber"] ?: "00000000"
        c.drawText(idNo, 30f, stripeH + 80f,
            DrawingUtils.paint(Color.parseColor("#006600"), 42f, bold = true))
        c.drawText("SERIAL: ${f["serialNo"] ?: "000000"}", 30f, stripeH + 112f,
            DrawingUtils.paint(Color.parseColor("#555555"), 20f))

        // Fields
        val lc = Color.parseColor("#555555"); val vc = Color.BLACK
        var y = stripeH + 145f; val x = 30f
        fun lv(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x, y, lc, vc, 16f, 26f)
            y += 52f
        }
        lv("FULL NAME", "${f["surname"]?.uppercase() ?: ""}  ${f["firstname"] ?: ""}")
        lv("DATE OF BIRTH", f["dob"] ?: "")
        lv("GENDER", f["gender"] ?: "")
        lv("DISTRICT OF BIRTH", f["district"] ?: "")

        var y2 = stripeH + 145f; val x2 = 450f
        fun lv2(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x2, y2, lc, vc, 16f, 26f)
            y2 += 52f
        }
        lv2("DIVISION", f["division"] ?: "")
        lv2("LOCATION", f["location"] ?: "")

        // Fingerprint
        DrawingUtils.fingerprint(c, 78f, H - 90f, 42f, Color.parseColor("#006600"), 50)
        c.drawText("RIGHT THUMB", 78f, H - 38f,
            DrawingUtils.paint(Color.parseColor("#555555"), 16f, align = Paint.Align.CENTER))

        // Bottom
        c.drawRect(0f, H - 45f, W.toFloat(), H.toFloat(), Paint().apply { color = Color.parseColor("#006600") })
        c.drawText("HAKI NA WAJIBU WA KILA MKENYA  •  ecitizen.go.ke",
            W / 2f, H - 16f, DrawingUtils.paint(Color.WHITE, 18f, align = Paint.Align.CENTER))

        return bmp
    }
}
