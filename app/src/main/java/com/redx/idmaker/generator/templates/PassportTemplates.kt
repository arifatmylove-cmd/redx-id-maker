package com.redx.idmaker.generator.templates

import android.graphics.*
import com.redx.idmaker.generator.IdCardData
import com.redx.idmaker.utils.DrawingUtils

object PassportTemplates {

    // Double-page spread: 1600 × 1120
    private const val W = 1600
    private const val H = 1120
    private const val MID = W / 2

    // ── NIGERIA PASSPORT ──────────────────────────────────────────────────────
    fun drawNigeriaPassport(data: IdCardData): Bitmap {
        val f = data.fields
        return genericPassport(
            data = data,
            leftBg = Color.parseColor("#006600"),
            rightBg = Color.parseColor("#003300"),
            accentColor = Color.parseColor("#FFD700"),
            countryCode = "NGA",
            countryName = "FEDERAL REPUBLIC OF NIGERIA",
            passportTitle = "PASSPORT  •  PASSEPORT",
            drawLeftEmblem = { c -> NigeriaTemplates.drawCoatOfArms(c, MID / 2f, H / 2f, 130f) },
            issuingOrg = "NIGERIA IMMIGRATION SERVICE"
        )
    }

    // ── UK PASSPORT ───────────────────────────────────────────────────────────
    fun drawUKPassport(data: IdCardData): Bitmap {
        return genericPassport(
            data = data,
            leftBg = Color.parseColor("#012169"),
            rightBg = Color.parseColor("#001845"),
            accentColor = Color.parseColor("#FFD700"),
            countryCode = "GBR",
            countryName = "UNITED KINGDOM OF GREAT BRITAIN AND NORTHERN IRELAND",
            passportTitle = "PASSPORT",
            drawLeftEmblem = { c ->
                DrawingUtils.emblem(c, MID / 2f, H / 2f, 120f, Color.parseColor("#FFD700"), Color.parseColor("#012169"), "ER")
            },
            issuingOrg = "HM PASSPORT OFFICE"
        )
    }

    // ── USA PASSPORT ──────────────────────────────────────────────────────────
    fun drawUSAPassport(data: IdCardData): Bitmap {
        return genericPassport(
            data = data,
            leftBg = Color.parseColor("#002868"),
            rightBg = Color.parseColor("#001840"),
            accentColor = Color.parseColor("#BF0A30"),
            countryCode = "USA",
            countryName = "UNITED STATES OF AMERICA",
            passportTitle = "PASSPORT",
            drawLeftEmblem = { c ->
                DrawingUtils.emblem(c, MID / 2f, H / 2f, 120f, Color.parseColor("#BF0A30"), Color.WHITE, "USA")
            },
            issuingOrg = "U.S. DEPARTMENT OF STATE"
        )
    }

    // ── GHANA PASSPORT ────────────────────────────────────────────────────────
    fun drawGhanaPassport(data: IdCardData): Bitmap {
        return genericPassport(
            data = data,
            leftBg = Color.parseColor("#006B3F"),
            rightBg = Color.parseColor("#004020"),
            accentColor = Color.parseColor("#FCD116"),
            countryCode = "GHA",
            countryName = "REPUBLIC OF GHANA",
            passportTitle = "PASSPORT",
            drawLeftEmblem = { c ->
                DrawingUtils.emblem(c, MID / 2f, H / 2f, 120f, Color.parseColor("#FCD116"), Color.parseColor("#006B3F"), "GH")
            },
            issuingOrg = "GHANA IMMIGRATION SERVICE"
        )
    }

    // ── KENYA PASSPORT ────────────────────────────────────────────────────────
    fun drawKenyaPassport(data: IdCardData): Bitmap {
        return genericPassport(
            data = data,
            leftBg = Color.parseColor("#006600"),
            rightBg = Color.parseColor("#003300"),
            accentColor = Color.parseColor("#BB0000"),
            countryCode = "KEN",
            countryName = "REPUBLIC OF KENYA",
            passportTitle = "PASSPORT  •  PASIPOTI",
            drawLeftEmblem = { c ->
                DrawingUtils.emblem(c, MID / 2f, H / 2f, 120f, Color.parseColor("#BB0000"), Color.WHITE, "KE")
            },
            issuingOrg = "KENYA IMMIGRATION SERVICE"
        )
    }

    // ── SOUTH AFRICA PASSPORT ─────────────────────────────────────────────────
    fun drawSouthAfricaPassport(data: IdCardData): Bitmap {
        return genericPassport(
            data = data,
            leftBg = Color.parseColor("#007749"),
            rightBg = Color.parseColor("#003D22"),
            accentColor = Color.parseColor("#FFB81C"),
            countryCode = "ZAF",
            countryName = "REPUBLIC OF SOUTH AFRICA",
            passportTitle = "PASSPORT  •  PASPOORT",
            drawLeftEmblem = { c ->
                DrawingUtils.emblem(c, MID / 2f, H / 2f, 120f, Color.parseColor("#003082"), Color.parseColor("#FFD700"), "SA")
            },
            issuingOrg = "DEPARTMENT OF HOME AFFAIRS"
        )
    }

    // ── INDIA PASSPORT ────────────────────────────────────────────────────────
    fun drawIndiaPassport(data: IdCardData): Bitmap {
        return genericPassport(
            data = data,
            leftBg = Color.parseColor("#003580"),
            rightBg = Color.parseColor("#00205B"),
            accentColor = Color.parseColor("#FF9933"),
            countryCode = "IND",
            countryName = "REPUBLIC OF INDIA  •  भारत",
            passportTitle = "PASSPORT  •  पासपोर्ट",
            drawLeftEmblem = { c ->
                DrawingUtils.emblem(c, MID / 2f, H / 2f, 120f, Color.parseColor("#FF9933"), Color.WHITE, "IN")
            },
            issuingOrg = "MINISTRY OF EXTERNAL AFFAIRS"
        )
    }

    // ── CANADA PASSPORT ───────────────────────────────────────────────────────
    fun drawCanadaPassport(data: IdCardData): Bitmap {
        return genericPassport(
            data = data,
            leftBg = Color.parseColor("#CC0000"),
            rightBg = Color.parseColor("#8B0000"),
            accentColor = Color.WHITE,
            countryCode = "CAN",
            countryName = "CANADA",
            passportTitle = "PASSPORT  •  PASSEPORT",
            drawLeftEmblem = { c ->
                DrawingUtils.emblem(c, MID / 2f, H / 2f, 120f, Color.WHITE, Color.parseColor("#CC0000"), "CA")
            },
            issuingOrg = "IMMIGRATION, REFUGEES AND CITIZENSHIP CANADA"
        )
    }

    // ── AUSTRALIA PASSPORT ────────────────────────────────────────────────────
    fun drawAustraliaPassport(data: IdCardData): Bitmap {
        return genericPassport(
            data = data,
            leftBg = Color.parseColor("#003087"),
            rightBg = Color.parseColor("#001A50"),
            accentColor = Color.parseColor("#FFD700"),
            countryCode = "AUS",
            countryName = "AUSTRALIA",
            passportTitle = "PASSPORT",
            drawLeftEmblem = { c ->
                DrawingUtils.emblem(c, MID / 2f, H / 2f, 120f, Color.parseColor("#FFD700"), Color.parseColor("#003087"), "AU")
            },
            issuingOrg = "DEPARTMENT OF HOME AFFAIRS"
        )
    }

    // ── GENERIC PASSPORT RENDERER ─────────────────────────────────────────────
    private fun genericPassport(
        data: IdCardData,
        leftBg: Int,
        rightBg: Int,
        accentColor: Int,
        countryCode: String,
        countryName: String,
        passportTitle: String,
        drawLeftEmblem: (Canvas) -> Unit,
        issuingOrg: String
    ): Bitmap {
        val bmp = Bitmap.createBitmap(W, H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        // ── LEFT PAGE (ECOWAS/country design page) ────────────────────────────
        c.drawRect(0f, 0f, MID.toFloat(), H.toFloat(), Paint().apply { color = leftBg })
        DrawingUtils.securityPattern(c, MID, H, countryCode, 25)

        // Watermark emblem
        drawLeftEmblem(c)

        // Left page header
        c.drawText(countryName, MID / 2f, 60f,
            DrawingUtils.paint(accentColor, 26f, bold = true, align = Paint.Align.CENTER))
        c.drawText(passportTitle, MID / 2f, 96f,
            DrawingUtils.paint(Color.WHITE, 22f, align = Paint.Align.CENTER))

        // Page lines (ruled)
        val linePaint = Paint().apply { color = Color.argb(60, 255, 255, 255); strokeWidth = 1f }
        for (i in 0..20) c.drawLine(40f, 150f + i * 40f, MID - 40f, 150f + i * 40f, linePaint)

        // Page number
        c.drawText("3", MID / 2f, H - 30f,
            DrawingUtils.paint(Color.WHITE, 26f, bold = true, align = Paint.Align.CENTER))

        // Spine line
        val spinePaint = Paint().apply { color = Color.argb(100, 255, 255, 255); strokeWidth = 8f }
        c.drawLine(MID.toFloat(), 0f, MID.toFloat(), H.toFloat(), spinePaint)

        // ── RIGHT PAGE (Data page) ─────────────────────────────────────────────
        c.drawRect(MID.toFloat(), 0f, W.toFloat(), H.toFloat(), Paint().apply { color = Color.parseColor("#FAFAF5") })
        DrawingUtils.securityPattern(c, W - MID, H, countryCode, 14)

        // Right page top bar
        c.drawRect(MID.toFloat(), 0f, W.toFloat(), 80f, Paint().apply { color = rightBg })
        c.drawText(countryName.take(50), MID + (W - MID) / 2f, 38f,
            DrawingUtils.paint(accentColor, 22f, bold = true, align = Paint.Align.CENTER))
        c.drawText("$passportTitle  |  TYPE: P  |  CODE: $countryCode",
            MID + (W - MID) / 2f, 68f,
            DrawingUtils.paint(Color.WHITE, 18f, align = Paint.Align.CENTER))

        // Photo area
        val px = MID + 40f; val py = 95f; val pw = 200; val ph = 255
        if (data.photo != null) {
            c.drawBitmap(DrawingUtils.roundBitmap(DrawingUtils.scaledPhoto(data.photo, pw, ph), 8f), px, py, null)
        } else {
            c.drawRect(px, py, px + pw, py + ph, Paint().apply { color = Color.parseColor("#DDDDDD") })
            c.drawText("PHOTO", px + pw / 2, py + ph / 2,
                DrawingUtils.paint(Color.GRAY, 26f, align = Paint.Align.CENTER))
        }
        c.drawRect(px, py, px + pw, py + ph, Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = rightBg; style = Paint.Style.STROKE; strokeWidth = 4f
        })

        // Data fields
        val lc = Color.parseColor("#555555")
        val vc = Color.parseColor("#111111")
        val fx = MID + 270f
        var fy = 102f
        fun passField(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, fx, fy, lc, vc, 18f, 30f)
            DrawingUtils.hline(c, fx, fy + 36f, (W - 40).toFloat(), Color.parseColor("#CCCCCC"), 1f)
            fy += 64f
        }
        passField("SURNAME / NOM", f["surname"]?.uppercase() ?: "")
        passField("GIVEN NAMES / PRÉNOMS", "${f["firstname"] ?: ""} ${f["middlename"] ?: ""}".trim())
        passField("NATIONALITY / NATIONALITÉ", f["nationality"] ?: countryCode)
        passField("DATE OF BIRTH / DATE DE NAISSANCE", f["dob"] ?: "")
        passField("PLACE OF BIRTH / LIEU DE NAISSANCE", f["placeOfBirth"] ?: "")
        passField("SEX / SEXE", f["gender"]?.take(1)?.uppercase() ?: "M")
        passField("PASSPORT NO. / No PASSEPORT", f["passportNo"] ?: "")
        passField("DATE OF ISSUE / DATE DE DÉLIVRANCE", f["issueDate"] ?: "")
        passField("DATE OF EXPIRY / DATE D'EXPIRATION", f["expiryDate"] ?: "")

        // Issuing authority
        c.drawText("ISSUING AUTHORITY:", fx, fy + 8f, DrawingUtils.paint(lc, 18f))
        c.drawText(issuingOrg, fx, fy + 44f, DrawingUtils.paint(vc, 26f, bold = true))

        // Signature line
        DrawingUtils.hline(c, px, py + ph + 28f, px + pw, rightBg, 1.5f)
        c.drawText("Signature", px, py + ph + 22f, DrawingUtils.paint(lc, 17f))

        // ── MRZ ZONE ──────────────────────────────────────────────────────────
        val mrzBg = Paint().apply { color = Color.parseColor("#F0EDE0") }
        c.drawRect(MID.toFloat(), H - 130f, W.toFloat(), H.toFloat(), mrzBg)
        DrawingUtils.hline(c, MID.toFloat(), H - 132f, W.toFloat(), Color.parseColor("#AAAAAA"), 1f)

        val surname = f["surname"] ?: "OKAFOR"
        val given = "${f["firstname"] ?: "EMEKA"} ${f["middlename"] ?: ""}".trim()
        val passNo = (f["passportNo"] ?: "A12345678").replace(" ", "").padEnd(9, '<').take(9)
        val dob = f["dob"]?.replace("/", "")?.let {
            if (it.length >= 8) it.substring(4, 6) + it.substring(6, 8) + it.substring(2, 4) else "900101"
        } ?: "900101"
        val expiry = f["expiryDate"]?.replace("/", "")?.let {
            if (it.length >= 8) it.substring(4, 6) + it.substring(6, 8) + it.substring(2, 4) else "300101"
        } ?: "300101"
        val sex = f["gender"]?.take(1)?.uppercase() ?: "M"
        val (mrz1, mrz2) = DrawingUtils.mrz(
            type = "P", countryCode = countryCode.take(3),
            surname = surname, given = given, number = passNo,
            nationality = countryCode.take(3), dob = dob, sex = sex, expiry = expiry
        )

        val mrzPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK; textSize = 34f
            typeface = Typeface.MONOSPACE
        }
        c.drawText("< < <  MACHINE READABLE ZONE  < < <", MID + (W - MID) / 2f, H - 108f,
            DrawingUtils.paint(Color.parseColor("#888888"), 16f, align = Paint.Align.CENTER))
        c.drawText(mrz1.take(44), MID + 30f, H - 78f, mrzPaint)
        c.drawText(mrz2.take(44), MID + 30f, H - 36f, mrzPaint)

        // Page number right
        c.drawText("4", MID + (W - MID) / 2f, H - 10f,
            DrawingUtils.paint(Color.parseColor("#888888"), 22f, align = Paint.Align.CENTER))

        return bmp
    }
}
