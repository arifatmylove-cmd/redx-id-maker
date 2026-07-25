package com.redx.idmaker.generator.templates

import android.graphics.*
import com.redx.idmaker.generator.IdCardData
import com.redx.idmaker.utils.DrawingUtils
import kotlin.math.*

object CanadaTemplates {

    private const val W = 1012
    private const val H = 638

    fun drawDriversLicence(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields
        val province = f["province"] ?: "Ontario"

        // Province colour scheme
        val (primary, accent) = when (province.lowercase()) {
            "british columbia" -> Pair("#003087", "#FFD700")
            "alberta"          -> Pair("#003087", "#FF0000")
            "quebec"           -> Pair("#003087", "#FFFFFF")
            else               -> Pair("#CC0000", "#FFFFFF")  // Ontario red
        }

        // White background
        c.drawColor(Color.WHITE)
        DrawingUtils.securityPattern(c, W, H, "CANADA DL", 12)

        // Red/province top bar
        c.drawRect(0f, 0f, W.toFloat(), 92f, Paint().apply { color = Color.parseColor(primary) })

        // Province + title
        c.drawText(province.uppercase(), W / 2f, 44f,
            DrawingUtils.paint(Color.parseColor(accent), 36f, bold = true, align = Paint.Align.CENTER))
        c.drawText("DRIVER'S LICENCE  •  PERMIS DE CONDUIRE", W / 2f, 80f,
            DrawingUtils.paint(Color.WHITE, 19f, align = Paint.Align.CENTER))

        // Maple leaf
        drawMapleLeaf(c, W - 90f, 46f, 48f, Color.parseColor("#CC0000"))

        // Red vertical stripe
        c.drawRect(0f, 92f, 16f, H.toFloat(), Paint().apply { color = Color.parseColor(primary) })

        // Photo
        val px = 30f; val py = 105f; val pw = 168; val ph = 210
        if (data.photo != null) {
            c.drawBitmap(DrawingUtils.roundBitmap(DrawingUtils.scaledPhoto(data.photo, pw, ph), 6f), px, py, null)
        } else {
            c.drawRect(px, py, px + pw, py + ph, Paint().apply { color = Color.parseColor("#DDDDDD") })
            c.drawText("PHOTO", px + pw / 2, py + ph / 2,
                DrawingUtils.paint(Color.GRAY, 24f, align = Paint.Align.CENTER))
        }
        c.drawRect(px, py, px + pw, py + ph, Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor(primary); style = Paint.Style.STROKE; strokeWidth = 3f
        })

        // Province seal
        DrawingUtils.emblem(c, 116f, H - 90f, 44f, Color.parseColor(primary), Color.WHITE, province.take(3).uppercase())

        val lc = Color.parseColor("#555555"); val vc = Color.BLACK
        var y = 112f; val x = 220f
        fun lv(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x, y, lc, vc, 16f, 26f)
            y += 52f
        }
        lv("NOM / SURNAME", f["surname"]?.uppercase() ?: "")
        lv("PRÉNOM / GIVEN NAME", f["firstname"] ?: "")
        lv("DATE DE NAISSANCE / DOB", f["dob"] ?: "")
        lv("SEXE / SEX", f["gender"]?.take(1) ?: "")
        lv("TAILLE / HEIGHT", "${f["height"] ?: "170"} cm")

        var y2 = 112f; val x2 = 580f
        fun lv2(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x2, y2, lc, vc, 16f, 26f)
            y2 += 52f
        }
        lv2("LICENCE NO. / №", f["licenceNo"] ?: "")
        lv2("CLASSE / CLASS", f["class"] ?: "G")
        lv2("DATE D'ÉMISSION / ISSUED", f["issueDate"] ?: "")
        lv2("EXPIRATION / EXPIRY", f["expiryDate"] ?: "")

        // Address
        c.drawText("ADRESSE / ADDRESS:", x, y, DrawingUtils.paint(lc, 16f))
        c.drawText((f["address"] ?: "").take(50), x, y + 28f, DrawingUtils.paint(vc, 22f, bold = true))

        // Bottom
        c.drawRect(16f, H - 55f, W.toFloat(), H.toFloat(), Paint().apply { color = Color.parseColor(primary) })
        c.drawText("CANADA  •  ${province.uppercase()}  •  NOT VALID IF LAMINATED",
            W / 2f, H - 22f, DrawingUtils.paint(Color.WHITE, 18f, align = Paint.Align.CENTER))

        return bmp
    }

    private fun drawMapleLeaf(canvas: Canvas, cx: Float, cy: Float, r: Float, color: Int) {
        val p = Paint(Paint.ANTI_ALIAS_FLAG).apply { this.color = color }
        val path = Path()
        // Simplified maple leaf via star-like shape with notched edges
        val points = 10
        for (i in 0 until points * 2) {
            val angle = i * Math.PI / points - Math.PI / 2
            val rad = if (i % 2 == 0) r else r * 0.5f
            val jitter = if (i % 4 == 0) rad * 0.2f else 0f
            val px = (cx + (rad + jitter) * cos(angle)).toFloat()
            val py = (cy + (rad + jitter) * sin(angle)).toFloat()
            if (i == 0) path.moveTo(px, py) else path.lineTo(px, py)
        }
        // Stem
        path.lineTo(cx + 4, cy + r * 1.3f)
        path.lineTo(cx - 4, cy + r * 1.3f)
        path.close()
        canvas.drawPath(path, p)
    }
}
