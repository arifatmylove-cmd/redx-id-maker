package com.redx.idmaker.data

object CountryData {

    private val genderOptions = listOf("Male", "Female")
    private val bloodGroupOptions = listOf("A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-")

    private fun commonFields(extra: List<DocumentField> = emptyList()) = listOf(
        DocumentField("surname", "Surname"),
        DocumentField("firstname", "First Name"),
        DocumentField("dob", "Date of Birth", FieldType.DATE),
        DocumentField("gender", "Gender", FieldType.DROPDOWN, options = genderOptions)
    ) + extra

    // ─── NIGERIA ───────────────────────────────────────────────────────────────
    private val nigeriaTypes = listOf(
        IdType(
            "ng_nin", "NIN Slip", IdCategory.NATIONAL_ID,
            commonFields(listOf(
                DocumentField("nin", "NIN (11 digits)"),
                DocumentField("phone", "Phone Number"),
                DocumentField("address", "Residential Address", FieldType.MULTILINE)
            ))
        ),
        IdType(
            "ng_nimc", "NIMC Card", IdCategory.NATIONAL_ID,
            commonFields(listOf(
                DocumentField("nin", "NIN (11 digits)"),
                DocumentField("middlename", "Middle Name", required = false),
                DocumentField("nationality", "Nationality")
            ))
        ),
        IdType(
            "ng_dl", "Driver's Licence", IdCategory.DRIVING_LICENCE,
            commonFields(listOf(
                DocumentField("middlename", "Middle Name", required = false),
                DocumentField("licenceNo", "Licence Number"),
                DocumentField("issueDate", "Issue Date", FieldType.DATE),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE),
                DocumentField("address", "Address", FieldType.MULTILINE),
                DocumentField("stateOfIssue", "State of Issue"),
                DocumentField("bloodGroup", "Blood Group", FieldType.DROPDOWN, options = bloodGroupOptions)
            ))
        ),
        IdType(
            "ng_pvc", "Permanent Voter's Card", IdCategory.VOTER_ID,
            commonFields(listOf(
                DocumentField("vin", "Voter Identification Number"),
                DocumentField("pollingUnit", "Polling Unit"),
                DocumentField("lga", "LGA"),
                DocumentField("state", "State")
            ))
        ),
        IdType(
            "ng_passport", "International Passport", IdCategory.PASSPORT,
            commonFields(listOf(
                DocumentField("middlename", "Middle Name", required = false),
                DocumentField("passportNo", "Passport Number"),
                DocumentField("issueDate", "Issue Date", FieldType.DATE),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE),
                DocumentField("placeOfBirth", "Place of Birth"),
                DocumentField("nationality", "Nationality")
            ))
        )
    )

    // ─── UK ────────────────────────────────────────────────────────────────────
    private val ukTypes = listOf(
        IdType(
            "uk_dl", "Driving Licence", IdCategory.DRIVING_LICENCE,
            commonFields(listOf(
                DocumentField("middlename", "Middle Name(s)", required = false),
                DocumentField("licenceNo", "Licence Number"),
                DocumentField("issueDate", "Issue Date", FieldType.DATE),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE),
                DocumentField("address", "Address", FieldType.MULTILINE),
                DocumentField("placeOfBirth", "Place of Birth"),
                DocumentField("height", "Height (cm)")
            ))
        ),
        IdType(
            "uk_brp", "Biometric Residence Permit", IdCategory.NATIONAL_ID,
            commonFields(listOf(
                DocumentField("nationality", "Nationality"),
                DocumentField("permitNo", "Permit Number"),
                DocumentField("issueDate", "Issue Date", FieldType.DATE),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE),
                DocumentField("immigrationStatus", "Immigration Status"),
                DocumentField("placeOfBirth", "Place of Birth")
            ))
        ),
        IdType(
            "uk_passport", "British Passport", IdCategory.PASSPORT,
            commonFields(listOf(
                DocumentField("middlename", "Middle Name(s)", required = false),
                DocumentField("passportNo", "Passport Number"),
                DocumentField("issueDate", "Issue Date", FieldType.DATE),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE),
                DocumentField("placeOfBirth", "Place of Birth"),
                DocumentField("nationality", "Nationality")
            ))
        )
    )

    // ─── USA ───────────────────────────────────────────────────────────────────
    private val usaStates = listOf("Kansas","California","Texas","New York","Florida","Illinois","Pennsylvania","Ohio","Georgia","North Carolina","Michigan")

    private val usaTypes = listOf(
        IdType(
            "us_dl", "Driver's License", IdCategory.DRIVING_LICENCE,
            commonFields(listOf(
                DocumentField("middlename", "Middle Name", required = false),
                DocumentField("licenceNo", "License Number"),
                DocumentField("issueDate", "Issue Date", FieldType.DATE),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE),
                DocumentField("address", "Address", FieldType.MULTILINE),
                DocumentField("state", "State", FieldType.DROPDOWN, options = usaStates),
                DocumentField("height", "Height (e.g. 5-10)"),
                DocumentField("eyeColor", "Eye Color"),
                DocumentField("class", "Class (e.g. C)"),
                DocumentField("donor", "Organ Donor", FieldType.DROPDOWN, options = listOf("Yes","No"))
            ))
        ),
        IdType(
            "us_passport", "US Passport", IdCategory.PASSPORT,
            commonFields(listOf(
                DocumentField("middlename", "Middle Name", required = false),
                DocumentField("passportNo", "Passport Number"),
                DocumentField("issueDate", "Issue Date", FieldType.DATE),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE),
                DocumentField("placeOfBirth", "Place of Birth"),
                DocumentField("nationality", "Nationality")
            ))
        )
    )

    // ─── GHANA ────────────────────────────────────────────────────────────────
    private val ghanaTypes = listOf(
        IdType(
            "gh_card", "Ghana Card", IdCategory.NATIONAL_ID,
            commonFields(listOf(
                DocumentField("middlename", "Middle Name", required = false),
                DocumentField("idNumber", "Card Number"),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE),
                DocumentField("nationality", "Nationality")
            ))
        ),
        IdType(
            "gh_voter", "Voter ID", IdCategory.VOTER_ID,
            commonFields(listOf(
                DocumentField("voterNo", "Voter ID Number"),
                DocumentField("region", "Region"),
                DocumentField("constituency", "Constituency")
            ))
        ),
        IdType(
            "gh_passport", "Ghana Passport", IdCategory.PASSPORT,
            commonFields(listOf(
                DocumentField("middlename", "Middle Name", required = false),
                DocumentField("passportNo", "Passport Number"),
                DocumentField("issueDate", "Issue Date", FieldType.DATE),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE),
                DocumentField("placeOfBirth", "Place of Birth"),
                DocumentField("nationality", "Nationality")
            ))
        )
    )

    // ─── KENYA ────────────────────────────────────────────────────────────────
    private val kenyaTypes = listOf(
        IdType(
            "ke_id", "National ID", IdCategory.NATIONAL_ID,
            commonFields(listOf(
                DocumentField("idNumber", "ID Number"),
                DocumentField("serialNo", "Serial Number"),
                DocumentField("district", "District of Birth"),
                DocumentField("division", "Division"),
                DocumentField("location", "Location")
            ))
        ),
        IdType(
            "ke_passport", "Kenya Passport", IdCategory.PASSPORT,
            commonFields(listOf(
                DocumentField("middlename", "Middle Name", required = false),
                DocumentField("passportNo", "Passport Number"),
                DocumentField("issueDate", "Issue Date", FieldType.DATE),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE),
                DocumentField("placeOfBirth", "Place of Birth"),
                DocumentField("nationality", "Nationality")
            ))
        )
    )

    // ─── SOUTH AFRICA ─────────────────────────────────────────────────────────
    private val saTypes = listOf(
        IdType(
            "za_id", "Smart ID Card", IdCategory.NATIONAL_ID,
            commonFields(listOf(
                DocumentField("idNumber", "ID Number (13 digits)"),
                DocumentField("nationality", "Nationality"),
                DocumentField("countryOfBirth", "Country of Birth"),
                DocumentField("citizenship", "Citizenship Status")
            ))
        ),
        IdType(
            "za_dl", "Driver's Licence", IdCategory.DRIVING_LICENCE,
            commonFields(listOf(
                DocumentField("licenceNo", "Licence Number"),
                DocumentField("issueDate", "Issue Date", FieldType.DATE),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE),
                DocumentField("codeAllowed", "Code Allowed (e.g. B)"),
                DocumentField("prDpCode", "PrDP Code", required = false)
            ))
        ),
        IdType(
            "za_passport", "South Africa Passport", IdCategory.PASSPORT,
            commonFields(listOf(
                DocumentField("middlename", "Middle Name", required = false),
                DocumentField("passportNo", "Passport Number"),
                DocumentField("issueDate", "Issue Date", FieldType.DATE),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE),
                DocumentField("placeOfBirth", "Place of Birth"),
                DocumentField("nationality", "Nationality")
            ))
        )
    )

    // ─── INDIA ────────────────────────────────────────────────────────────────
    private val indiaTypes = listOf(
        IdType(
            "in_aadhaar", "Aadhaar Card", IdCategory.NATIONAL_ID,
            commonFields(listOf(
                DocumentField("aadhaarNo", "Aadhaar Number (12 digits)"),
                DocumentField("address", "Address", FieldType.MULTILINE),
                DocumentField("pinCode", "PIN Code")
            ))
        ),
        IdType(
            "in_pan", "PAN Card", IdCategory.NATIONAL_ID,
            listOf(
                DocumentField("surname", "Last Name"),
                DocumentField("firstname", "First Name"),
                DocumentField("fathersName", "Father's Name"),
                DocumentField("dob", "Date of Birth", FieldType.DATE),
                DocumentField("panNo", "PAN Number")
            )
        ),
        IdType(
            "in_passport", "Indian Passport", IdCategory.PASSPORT,
            commonFields(listOf(
                DocumentField("middlename", "Middle Name", required = false),
                DocumentField("passportNo", "Passport Number"),
                DocumentField("issueDate", "Issue Date", FieldType.DATE),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE),
                DocumentField("placeOfBirth", "Place of Birth"),
                DocumentField("nationality", "Nationality")
            ))
        )
    )

    // ─── CANADA ───────────────────────────────────────────────────────────────
    private val canadaProvinces = listOf("Ontario","Quebec","British Columbia","Alberta","Manitoba","Saskatchewan","Nova Scotia","New Brunswick","PEI","Newfoundland","Northwest Territories","Yukon","Nunavut")

    private val canadaTypes = listOf(
        IdType(
            "ca_dl", "Driver's Licence", IdCategory.DRIVING_LICENCE,
            commonFields(listOf(
                DocumentField("licenceNo", "Licence Number"),
                DocumentField("issueDate", "Issue Date", FieldType.DATE),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE),
                DocumentField("address", "Address", FieldType.MULTILINE),
                DocumentField("province", "Province", FieldType.DROPDOWN, options = canadaProvinces),
                DocumentField("class", "Class"),
                DocumentField("height", "Height (cm)")
            ))
        ),
        IdType(
            "ca_passport", "Canadian Passport", IdCategory.PASSPORT,
            commonFields(listOf(
                DocumentField("middlename", "Middle Name", required = false),
                DocumentField("passportNo", "Passport Number"),
                DocumentField("issueDate", "Issue Date", FieldType.DATE),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE),
                DocumentField("placeOfBirth", "Place of Birth"),
                DocumentField("nationality", "Nationality")
            ))
        )
    )

    // ─── AUSTRALIA ────────────────────────────────────────────────────────────
    private val ausStates = listOf("New South Wales","Victoria","Queensland","Western Australia","South Australia","Tasmania","ACT","Northern Territory")

    private val australiaTypes = listOf(
        IdType(
            "au_dl", "Driver Licence", IdCategory.DRIVING_LICENCE,
            commonFields(listOf(
                DocumentField("licenceNo", "Licence Number"),
                DocumentField("issueDate", "Issue Date", FieldType.DATE),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE),
                DocumentField("address", "Address", FieldType.MULTILINE),
                DocumentField("state", "State", FieldType.DROPDOWN, options = ausStates),
                DocumentField("licenceClass", "Class"),
                DocumentField("height", "Height (cm)")
            ))
        ),
        IdType(
            "au_passport", "Australian Passport", IdCategory.PASSPORT,
            commonFields(listOf(
                DocumentField("middlename", "Middle Name", required = false),
                DocumentField("passportNo", "Passport Number"),
                DocumentField("issueDate", "Issue Date", FieldType.DATE),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE),
                DocumentField("placeOfBirth", "Place of Birth"),
                DocumentField("nationality", "Nationality")
            ))
        )
    )

    // ─── COMPANY DOCS ─────────────────────────────────────────────────────────
    private val companyTypes = listOf(
        IdType(
            "co_cert", "Certificate of Incorporation", IdCategory.COMPANY,
            listOf(
                DocumentField("companyName", "Company Name"),
                DocumentField("rcNumber", "RC Number"),
                DocumentField("dateIncorp", "Date of Incorporation", FieldType.DATE),
                DocumentField("companyType", "Company Type", FieldType.DROPDOWN, options = listOf("Private Limited","Public Limited","NGO","Partnership")),
                DocumentField("address", "Registered Address", FieldType.MULTILINE),
                DocumentField("directors", "Director(s) Full Name(s)", FieldType.MULTILINE)
            )
        ),
        IdType(
            "co_empid", "Employee ID Card", IdCategory.COMPANY,
            listOf(
                DocumentField("companyName", "Company Name"),
                DocumentField("employeeName", "Employee Full Name"),
                DocumentField("jobTitle", "Job Title / Designation"),
                DocumentField("employeeId", "Employee ID"),
                DocumentField("department", "Department"),
                DocumentField("issueDate", "Issue Date", FieldType.DATE),
                DocumentField("expiryDate", "Expiry Date", FieldType.DATE)
            )
        ),
        IdType(
            "co_breg", "Business Registration", IdCategory.COMPANY,
            listOf(
                DocumentField("businessName", "Business Name"),
                DocumentField("bnNumber", "BN Number"),
                DocumentField("dateReg", "Date of Registration", FieldType.DATE),
                DocumentField("businessType", "Business Nature"),
                DocumentField("address", "Business Address", FieldType.MULTILINE),
                DocumentField("proprietor", "Proprietor / Owner Name")
            )
        ),
        IdType(
            "co_letter", "Letter of Introduction", IdCategory.COMPANY,
            listOf(
                DocumentField("companyName", "Company Name"),
                DocumentField("recipientName", "Recipient Name / Organisation"),
                DocumentField("staffName", "Staff Name"),
                DocumentField("staffTitle", "Staff Title"),
                DocumentField("purpose", "Purpose of Letter", FieldType.MULTILINE),
                DocumentField("date", "Letter Date", FieldType.DATE)
            )
        )
    )

    val countries = listOf(
        Country("NG", "Nigeria", "🇳🇬", nigeriaTypes),
        Country("GB", "United Kingdom", "🇬🇧", ukTypes),
        Country("US", "United States", "🇺🇸", usaTypes),
        Country("GH", "Ghana", "🇬🇭", ghanaTypes),
        Country("KE", "Kenya", "🇰🇪", kenyaTypes),
        Country("ZA", "South Africa", "🇿🇦", saTypes),
        Country("IN", "India", "🇮🇳", indiaTypes),
        Country("CA", "Canada", "🇨🇦", canadaTypes),
        Country("AU", "Australia", "🇦🇺", australiaTypes),
        Country("CO", "Company Docs", "🏢", companyTypes)
    )
}
