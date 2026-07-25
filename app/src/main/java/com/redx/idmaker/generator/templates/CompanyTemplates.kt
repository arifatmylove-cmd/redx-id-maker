package com.redx.idmaker.generator.templates

import android.graphics.*
import com.redx.idmaker.generator.IdCardData
import com.redx.idmaker.utils.DrawingUtils

object CompanyTemplates {

    // A4 landscape for docs, portrait card for Employee ID
    private const val DOC_W = 1400
    private const val DOC_H = 1980
    private const val EMP_W = 700
    private const val EMP_H = 1050

    // ── CERTIFICATE OF INCORPORATION ─────────────────────────────────────────
    fun drawCertificate(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(DOC_W, DOC_H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        // Cream parchment background
        c.drawColor(Color.parseColor("#FEFAEE"))

        // Ornamental double border
        val outerBorder = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#8B6914"); style = Paint.Style.STROKE; strokeWidth = 18f
        }
        val innerBorder = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#D4AF37"); style = Paint.Style.STROKE; strokeWidth = 6f
        }
        c.drawRect(24f, 24f, DOC_W - 24f, DOC_H - 24f, outerBorder)
        c.drawRect(46f, 46f, DOC_W - 46f, DOC_H - 46f, innerBorder)
        c.drawRect(58f, 58f, DOC_W - 58f, DOC_H - 58f, outerBorder.apply { strokeWidth = 4f })

        // Corner ornaments
        val corners = listOf(
            Pair(80f, 80f), Pair(DOC_W - 80f, 80f),
            Pair(80f, DOC_H - 80f), Pair(DOC_W - 80f, DOC_H - 80f)
        )
        corners.forEach { (cx, cy) ->
            DrawingUtils.drawStar(c, cx, cy, 28f, 12f, 8,
                Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#D4AF37") })
        }

        // Header
        val companyName = f["companyName"] ?: "ACME CORPORATION LTD"
        val rcNo = f["rcNumber"] ?: "RC 123456"
        val companyType = f["companyType"] ?: "Private Limited"
        val dateIncorp = f["dateIncorp"] ?: ""
        val directors = f["directors"] ?: "Director Name"

        // Emblem / Seal
        DrawingUtils.emblem(c, DOC_W / 2f, 220f, 100f, Color.parseColor("#8B6914"), Color.parseColor("#FEFAEE"), "CAC")

        c.drawText("CORPORATE AFFAIRS COMMISSION", DOC_W / 2f, 360f,
            DrawingUtils.paint(Color.parseColor("#8B6914"), 46f, bold = true, align = Paint.Align.CENTER))
        c.drawText("FEDERAL REPUBLIC OF NIGERIA", DOC_W / 2f, 415f,
            DrawingUtils.paint(Color.parseColor("#333333"), 36f, align = Paint.Align.CENTER))

        DrawingUtils.hline(c, 100f, 445f, (DOC_W - 100).toFloat(), Color.parseColor("#D4AF37"), 4f)

        c.drawText("CERTIFICATE OF INCORPORATION", DOC_W / 2f, 510f,
            DrawingUtils.paint(Color.parseColor("#8B6914"), 54f, bold = true, align = Paint.Align.CENTER))

        DrawingUtils.hline(c, 100f, 530f, (DOC_W - 100).toFloat(), Color.parseColor("#D4AF37"), 4f)

        // Body text
        val bodyPaint = DrawingUtils.paint(Color.parseColor("#222222"), 36f)
        bodyPaint.textAlign = Paint.Align.CENTER
        c.drawText("This is to Certify that", DOC_W / 2f, 610f, bodyPaint)

        // Company name
        c.drawText(companyName.uppercase(), DOC_W / 2f, 690f,
            DrawingUtils.paint(Color.parseColor("#003087"), 52f, bold = true, align = Paint.Align.CENTER))
        DrawingUtils.hline(c, 200f, 710f, (DOC_W - 200).toFloat(), Color.parseColor("#003087"), 3f)

        // Type and RC
        c.drawText("a $companyType company", DOC_W / 2f, 770f, bodyPaint)
        c.drawText("is this day incorporated under the Companies and Allied Matters Act,", DOC_W / 2f, 830f, bodyPaint)
        c.drawText("Cap C20, Laws of the Federation of Nigeria.", DOC_W / 2f, 890f, bodyPaint)

        c.drawText("Registration Number:", DOC_W / 2f, 980f,
            DrawingUtils.paint(Color.parseColor("#555555"), 36f, align = Paint.Align.CENTER))
        c.drawText(rcNo, DOC_W / 2f, 1052f,
            DrawingUtils.paint(Color.parseColor("#8B6914"), 64f, bold = true, align = Paint.Align.CENTER))

        c.drawText("Date of Incorporation:", DOC_W / 2f, 1140f,
            DrawingUtils.paint(Color.parseColor("#555555"), 36f, align = Paint.Align.CENTER))
        c.drawText(dateIncorp, DOC_W / 2f, 1212f,
            DrawingUtils.paint(Color.parseColor("#222222"), 44f, bold = true, align = Paint.Align.CENTER))

        DrawingUtils.hline(c, 100f, 1260f, (DOC_W - 100).toFloat(), Color.parseColor("#D4AF37"), 2f)

        // Directors
        c.drawText("DIRECTOR(S):", DOC_W / 2f, 1320f,
            DrawingUtils.paint(Color.parseColor("#8B6914"), 36f, bold = true, align = Paint.Align.CENTER))
        c.drawText(directors, DOC_W / 2f, 1385f,
            DrawingUtils.paint(Color.parseColor("#111111"), 38f, bold = true, align = Paint.Align.CENTER))

        // Registered address
        c.drawText("Registered Office:", DOC_W / 2f, 1470f,
            DrawingUtils.paint(Color.parseColor("#555555"), 34f, align = Paint.Align.CENTER))
        DrawingUtils.wrapText(c, f["address"] ?: "Address not provided",
            DOC_W / 2f, 1520f, (DOC_W - 200).toFloat(),
            DrawingUtils.paint(Color.parseColor("#222222"), 32f, align = Paint.Align.CENTER), 44f)

        // Seal
        DrawingUtils.emblem(c, DOC_W / 2f, 1760f, 80f, Color.parseColor("#D4AF37"), Color.parseColor("#8B6914"), "SEAL")

        // Signature lines
        DrawingUtils.hline(c, 200f, 1870f, 650f, Color.BLACK, 2f)
        c.drawText("Registrar-General", 425f, 1900f,
            DrawingUtils.paint(Color.parseColor("#333333"), 30f, align = Paint.Align.CENTER))

        DrawingUtils.hline(c, 750f, 1870f, 1200f, Color.BLACK, 2f)
        c.drawText("Date", 975f, 1900f,
            DrawingUtils.paint(Color.parseColor("#333333"), 30f, align = Paint.Align.CENTER))

        // Footer
        c.drawText("cac.gov.ng  •  Abuja, Nigeria", DOC_W / 2f, DOC_H - 70f,
            DrawingUtils.paint(Color.parseColor("#888888"), 28f, align = Paint.Align.CENTER))

        return bmp
    }

    // ── EMPLOYEE ID CARD ──────────────────────────────────────────────────────
    fun drawEmployeeID(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(EMP_W, EMP_H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        val compName = f["companyName"] ?: "REDX CORP"
        val empName = f["employeeName"] ?: "${f["firstname"] ?: ""} ${f["surname"] ?: ""}".trim()
        val jobTitle = f["jobTitle"] ?: "Staff"
        val empId = f["employeeId"] ?: "EMP-0000"
        val dept = f["department"] ?: ""
        val expiry = f["expiryDate"] ?: ""

        // Dark gradient background
        val grad = LinearGradient(0f, 0f, 0f, EMP_H.toFloat(),
            Color.parseColor("#1A2744"), Color.parseColor("#0D1A38"), Shader.TileMode.CLAMP)
        c.drawRect(0f, 0f, EMP_W.toFloat(), EMP_H.toFloat(), Paint().apply { shader = grad })
        DrawingUtils.securityPattern(c, EMP_W, EMP_H, compName.take(6), 20)

        // Top gold bar
        c.drawRect(0f, 0f, EMP_W.toFloat(), 16f, Paint().apply { color = Color.parseColor("#D4AF37") })

        // Company logo circle
        DrawingUtils.emblem(c, EMP_W / 2f, 110f, 75f, Color.parseColor("#D4AF37"), Color.parseColor("#1A2744"),
            compName.take(4))

        // Company name
        c.drawText(compName.uppercase(), EMP_W / 2f, 215f,
            DrawingUtils.paint(Color.parseColor("#D4AF37"), 32f, bold = true, align = Paint.Align.CENTER))
        DrawingUtils.hline(c, 60f, 230f, (EMP_W - 60).toFloat(), Color.parseColor("#D4AF37"), 2f)

        // ID badge label
        c.drawText("EMPLOYEE IDENTIFICATION", EMP_W / 2f, 268f,
            DrawingUtils.paint(Color.WHITE, 20f, align = Paint.Align.CENTER))

        // Photo
        val pw = 200; val ph = 240
        val px = (EMP_W - pw) / 2f; val py = 285f
        if (data.photo != null) {
            c.drawBitmap(DrawingUtils.roundBitmap(DrawingUtils.scaledPhoto(data.photo, pw, ph), 10f), px, py, null)
        } else {
            val ph2 = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.parseColor("#2A3A60") }
            c.drawRoundRect(px, py, px + pw, py + ph, 10f, 10f, ph2)
            c.drawText("PHOTO", px + pw / 2, py + ph / 2,
                DrawingUtils.paint(Color.parseColor("#AAAAAA"), 26f, align = Paint.Align.CENTER))
        }
        c.drawRoundRect(px, py, px + pw, py + ph, 10f, 10f, Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#D4AF37"); style = Paint.Style.STROKE; strokeWidth = 4f
        })

        // Name + Title
        c.drawText(empName.uppercase(), EMP_W / 2f, 560f,
            DrawingUtils.paint(Color.WHITE, 32f, bold = true, align = Paint.Align.CENTER))
        c.drawText(jobTitle, EMP_W / 2f, 598f,
            DrawingUtils.paint(Color.parseColor("#D4AF37"), 24f, align = Paint.Align.CENTER))

        DrawingUtils.hline(c, 60f, 615f, (EMP_W - 60).toFloat(), Color.parseColor("#334466"), 1f)

        // Fields
        val lc = Color.parseColor("#AAAACC"); val vc = Color.WHITE
        var y = 645f; val x = 60f
        fun lv(label: String, value: String) {
            DrawingUtils.labelAndValue(c, label, value, x, y, lc, vc, 18f, 28f)
            y += 58f
        }
        lv("EMPLOYEE ID", empId)
        lv("DEPARTMENT", dept)
        lv("VALID UNTIL", expiry)

        // QR Code
        val qrData = "EMP:$empId|$empName|$compName|$jobTitle"
        val qr = DrawingUtils.generateQRCode(qrData, 140)
        c.drawBitmap(qr, (EMP_W - 140) / 2f, y + 20f, null)
        c.drawText("Scan to Verify", EMP_W / 2f, y + 175f,
            DrawingUtils.paint(Color.parseColor("#AAAAAA"), 18f, align = Paint.Align.CENTER))

        // Bottom gold bar + holder name
        c.drawRect(0f, EMP_H - 50f, EMP_W.toFloat(), EMP_H - 16f, Paint().apply { color = Color.parseColor("#D4AF37") })
        c.drawRect(0f, EMP_H - 16f, EMP_W.toFloat(), EMP_H.toFloat(), Paint().apply { color = Color.parseColor("#8B6914") })
        c.drawText("NOT TRANSFERABLE  •  ${compName.uppercase()}", EMP_W / 2f, EMP_H - 26f,
            DrawingUtils.paint(Color.parseColor("#1A2744"), 18f, bold = true, align = Paint.Align.CENTER))

        return bmp
    }

    // ── BUSINESS REGISTRATION ─────────────────────────────────────────────────
    fun drawBusinessReg(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(DOC_W, DOC_H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        c.drawColor(Color.parseColor("#F8FFF8"))

        // Green borders
        val greenBorder = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.parseColor("#006600"); style = Paint.Style.STROKE; strokeWidth = 20f
        }
        c.drawRect(28f, 28f, DOC_W - 28f, DOC_H - 28f, greenBorder)
        c.drawRect(52f, 52f, DOC_W - 52f, DOC_H - 52f,
            greenBorder.apply { strokeWidth = 4f; color = Color.parseColor("#008000") })

        // CAC logo
        DrawingUtils.emblem(c, DOC_W / 2f, 200f, 90f, Color.parseColor("#006600"), Color.WHITE, "CAC")

        c.drawText("CORPORATE AFFAIRS COMMISSION", DOC_W / 2f, 330f,
            DrawingUtils.paint(Color.parseColor("#006600"), 44f, bold = true, align = Paint.Align.CENTER))
        c.drawText("FEDERAL REPUBLIC OF NIGERIA", DOC_W / 2f, 385f,
            DrawingUtils.paint(Color.parseColor("#333333"), 34f, align = Paint.Align.CENTER))

        DrawingUtils.hline(c, 100f, 415f, (DOC_W - 100).toFloat(), Color.parseColor("#006600"), 4f)

        c.drawText("CERTIFICATE OF BUSINESS NAME REGISTRATION", DOC_W / 2f, 490f,
            DrawingUtils.paint(Color.parseColor("#006600"), 46f, bold = true, align = Paint.Align.CENTER))

        DrawingUtils.hline(c, 100f, 510f, (DOC_W - 100).toFloat(), Color.parseColor("#006600"), 4f)

        val body = DrawingUtils.paint(Color.parseColor("#222222"), 36f, align = Paint.Align.CENTER)
        c.drawText("This certifies that the Business Name", DOC_W / 2f, 590f, body)

        val bizName = f["businessName"] ?: "MY BUSINESS ENTERPRISE"
        c.drawText(bizName.uppercase(), DOC_W / 2f, 670f,
            DrawingUtils.paint(Color.parseColor("#003087"), 52f, bold = true, align = Paint.Align.CENTER))
        DrawingUtils.hline(c, 200f, 695f, (DOC_W - 200).toFloat(), Color.parseColor("#003087"), 3f)

        c.drawText("has been duly registered pursuant to the Business Names Act,", DOC_W / 2f, 770f, body)
        c.drawText("Cap B22, Laws of the Federation of Nigeria.", DOC_W / 2f, 825f, body)

        c.drawText("BN Number:", DOC_W / 2f, 920f,
            DrawingUtils.paint(Color.parseColor("#555555"), 36f, align = Paint.Align.CENTER))
        c.drawText(f["bnNumber"] ?: "BN 0000000", DOC_W / 2f, 990f,
            DrawingUtils.paint(Color.parseColor("#006600"), 62f, bold = true, align = Paint.Align.CENTER))

        c.drawText("Date of Registration:", DOC_W / 2f, 1080f,
            DrawingUtils.paint(Color.parseColor("#555555"), 36f, align = Paint.Align.CENTER))
        c.drawText(f["dateReg"] ?: "", DOC_W / 2f, 1150f,
            DrawingUtils.paint(Color.parseColor("#222222"), 44f, bold = true, align = Paint.Align.CENTER))

        c.drawText("Nature of Business:", DOC_W / 2f, 1240f,
            DrawingUtils.paint(Color.parseColor("#555555"), 36f, align = Paint.Align.CENTER))
        c.drawText(f["businessType"] ?: "", DOC_W / 2f, 1310f,
            DrawingUtils.paint(Color.parseColor("#222222"), 40f, bold = true, align = Paint.Align.CENTER))

        c.drawText("Proprietor / Owner:", DOC_W / 2f, 1400f,
            DrawingUtils.paint(Color.parseColor("#555555"), 36f, align = Paint.Align.CENTER))
        c.drawText((f["proprietor"] ?: "").uppercase(), DOC_W / 2f, 1470f,
            DrawingUtils.paint(Color.parseColor("#111111"), 44f, bold = true, align = Paint.Align.CENTER))

        c.drawText("Business Address:", DOC_W / 2f, 1560f,
            DrawingUtils.paint(Color.parseColor("#555555"), 36f, align = Paint.Align.CENTER))
        DrawingUtils.wrapText(c, f["address"] ?: "", DOC_W / 2f, 1620f, (DOC_W - 200).toFloat(),
            DrawingUtils.paint(Color.parseColor("#222222"), 32f, align = Paint.Align.CENTER), 44f)

        DrawingUtils.emblem(c, DOC_W / 2f, 1780f, 70f, Color.parseColor("#006600"), Color.WHITE, "SEAL")
        DrawingUtils.hline(c, 200f, 1870f, 650f, Color.BLACK, 2f)
        c.drawText("Registrar-General", 425f, 1905f,
            DrawingUtils.paint(Color.parseColor("#333333"), 30f, align = Paint.Align.CENTER))
        DrawingUtils.hline(c, 750f, 1870f, 1200f, Color.BLACK, 2f)
        c.drawText("Date", 975f, 1905f,
            DrawingUtils.paint(Color.parseColor("#333333"), 30f, align = Paint.Align.CENTER))

        c.drawText("cac.gov.ng  •  This document is not valid if altered",
            DOC_W / 2f, DOC_H - 70f,
            DrawingUtils.paint(Color.parseColor("#888888"), 28f, align = Paint.Align.CENTER))

        return bmp
    }

    // ── LETTER OF INTRODUCTION ────────────────────────────────────────────────
    fun drawLetter(data: IdCardData): Bitmap {
        val bmp = Bitmap.createBitmap(DOC_W, DOC_H, Bitmap.Config.ARGB_8888)
        val c = Canvas(bmp)
        val f = data.fields

        c.drawColor(Color.WHITE)

        // Letterhead top bar
        val compName = f["companyName"] ?: "COMPANY NAME LTD"
        c.drawRect(0f, 0f, DOC_W.toFloat(), 160f, Paint().apply { color = Color.parseColor("#1A2744") })
        DrawingUtils.emblem(c, 100f, 80f, 55f, Color.parseColor("#D4AF37"), Color.parseColor("#1A2744"),
            compName.take(2).uppercase())

        c.drawText(compName.uppercase(), DOC_W / 2f, 72f,
            DrawingUtils.paint(Color.parseColor("#D4AF37"), 44f, bold = true, align = Paint.Align.CENTER))
        c.drawText("www.company.com  •  info@company.com  •  +234 800 000 0000",
            DOC_W / 2f, 128f,
            DrawingUtils.paint(Color.parseColor("#AADDFF"), 26f, align = Paint.Align.CENTER))

        DrawingUtils.hline(c, 0f, 160f, DOC_W.toFloat(), Color.parseColor("#D4AF37"), 5f)

        // Date
        c.drawText("Date: ${f["date"] ?: ""}", DOC_W - 100f, 230f,
            DrawingUtils.paint(Color.parseColor("#333333"), 30f, align = Paint.Align.RIGHT))

        // Ref
        c.drawText("Ref: INTRO/${System.currentTimeMillis() % 10000}", 100f, 230f,
            DrawingUtils.paint(Color.parseColor("#333333"), 30f))

        // Recipient
        c.drawText("To:", 100f, 310f, DrawingUtils.paint(Color.parseColor("#333333"), 30f))
        c.drawText(f["recipientName"] ?: "The Manager", 100f, 355f,
            DrawingUtils.paint(Color.parseColor("#111111"), 34f, bold = true))

        // Subject
        DrawingUtils.hline(c, 100f, 390f, (DOC_W - 100).toFloat(), Color.parseColor("#CCCCCC"), 1f)
        c.drawText("SUBJECT: LETTER OF INTRODUCTION — ${(f["staffName"] ?: "").uppercase()}", 100f, 435f,
            DrawingUtils.paint(Color.parseColor("#1A2744"), 34f, bold = true))
        DrawingUtils.hline(c, 100f, 455f, (DOC_W - 100).toFloat(), Color.parseColor("#CCCCCC"), 1f)

        // Salutation
        c.drawText("Dear Sir/Madam,", 100f, 520f, DrawingUtils.paint(Color.parseColor("#222222"), 32f))

        // Body
        val staffName = f["staffName"] ?: "our member of staff"
        val staffTitle = f["staffTitle"] ?: "representative"
        val purpose = f["purpose"] ?: "official duties."

        val bodyText = "This is to formally introduce $staffName, who holds the position of $staffTitle " +
                "in our organisation, $compName. ${staffName.split(" ").first()} has been duly authorised " +
                "to represent the company in all matters pertaining to: $purpose\n\n" +
                "We kindly request that you extend to ${staffName.split(" ").first()} every necessary " +
                "assistance and cooperation required to facilitate the successful completion of the " +
                "aforementioned assignment.\n\n" +
                "For further enquiries, please do not hesitate to contact us through the details " +
                "provided in our letterhead above.\n\n" +
                "We thank you for your kind assistance and look forward to a continued cordial " +
                "working relationship."

        val bodyPaint = DrawingUtils.paint(Color.parseColor("#222222"), 30f)
        var textY = 580f
        bodyText.split("\n").forEach { paragraph ->
            if (paragraph.isEmpty()) { textY += 30f; return@forEach }
            textY = DrawingUtils.wrapText(c, paragraph, 100f, textY, (DOC_W - 200).toFloat(), bodyPaint, 44f)
            textY += 10f
        }

        // Closing
        textY += 40f
        c.drawText("Yours faithfully,", 100f, textY, DrawingUtils.paint(Color.parseColor("#222222"), 32f))
        textY += 140f
        DrawingUtils.hline(c, 100f, textY, 450f, Color.BLACK, 2f)
        textY += 38f
        c.drawText(f["staffTitle"] ?: "Authorised Signatory", 100f, textY,
            DrawingUtils.paint(Color.parseColor("#333333"), 28f))
        textY += 40f
        c.drawText(compName, 100f, textY, DrawingUtils.paint(Color.parseColor("#1A2744"), 30f, bold = true))

        // Footer
        c.drawRect(0f, DOC_H - 100f, DOC_W.toFloat(), DOC_H.toFloat(),
            Paint().apply { color = Color.parseColor("#1A2744") })
        c.drawText("$compName  •  CONFIDENTIAL",
            DOC_W / 2f, DOC_H - 55f,
            DrawingUtils.paint(Color.parseColor("#AADDFF"), 28f, align = Paint.Align.CENTER))
        c.drawText("This letter is issued on company letterhead and is valid only for the stated purpose.",
            DOC_W / 2f, DOC_H - 22f,
            DrawingUtils.paint(Color.parseColor("#6688AA"), 24f, align = Paint.Align.CENTER))

        return bmp
    }
}
