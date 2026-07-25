package com.redx.idmaker.generator

import android.graphics.Bitmap
import com.redx.idmaker.generator.templates.*

object IdCardGenerator {
    fun generate(data: IdCardData): Bitmap {
        return when (data.idTypeId) {
            // Nigeria
            "ng_nin"      -> NigeriaTemplates.drawNinSlip(data)
            "ng_nimc"     -> NigeriaTemplates.drawNIMC(data)
            "ng_dl"       -> NigeriaTemplates.drawDriversLicence(data)
            "ng_pvc"      -> NigeriaTemplates.drawPVC(data)
            "ng_passport" -> PassportTemplates.drawNigeriaPassport(data)
            // UK
            "uk_dl"       -> UkTemplates.drawDrivingLicence(data)
            "uk_brp"      -> UkTemplates.drawResidencePermit(data)
            "uk_passport" -> PassportTemplates.drawUKPassport(data)
            // USA
            "us_dl"       -> UsaTemplates.drawDriversLicense(data)
            "us_passport" -> PassportTemplates.drawUSAPassport(data)
            // Ghana
            "gh_card"     -> GhanaTemplates.drawGhanaCard(data)
            "gh_voter"    -> GhanaTemplates.drawVoterID(data)
            "gh_passport" -> PassportTemplates.drawGhanaPassport(data)
            // Kenya
            "ke_id"       -> KenyaTemplates.drawNationalID(data)
            "ke_passport" -> PassportTemplates.drawKenyaPassport(data)
            // South Africa
            "za_id"       -> SouthAfricaTemplates.drawSmartID(data)
            "za_dl"       -> SouthAfricaTemplates.drawDriversLicence(data)
            "za_passport" -> PassportTemplates.drawSouthAfricaPassport(data)
            // India
            "in_aadhaar"  -> IndiaTemplates.drawAadhaar(data)
            "in_pan"      -> IndiaTemplates.drawPAN(data)
            "in_passport" -> PassportTemplates.drawIndiaPassport(data)
            // Canada
            "ca_dl"       -> CanadaTemplates.drawDriversLicence(data)
            "ca_passport" -> PassportTemplates.drawCanadaPassport(data)
            // Australia
            "au_dl"       -> AustraliaTemplates.drawDriversLicence(data)
            "au_passport" -> PassportTemplates.drawAustraliaPassport(data)
            // Company
            "co_cert"     -> CompanyTemplates.drawCertificate(data)
            "co_empid"    -> CompanyTemplates.drawEmployeeID(data)
            "co_breg"     -> CompanyTemplates.drawBusinessReg(data)
            "co_letter"   -> CompanyTemplates.drawLetter(data)
            else          -> NigeriaTemplates.drawNinSlip(data)
        }
    }
}
