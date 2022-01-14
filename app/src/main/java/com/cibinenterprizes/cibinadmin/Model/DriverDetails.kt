package com.cibinenterprizes.cibinadmin.Model

class DriverDetails {
    var Username: String? = null
    var EmailId: String? = null
    var Mobile: String? = null
    var ProfilePhoto: String? = null
    var CDid: String? = null

    constructor(){

    }

    constructor(Username: String?, EmailId: String?, Mobile: String?, ProfilePhoto: String?, CDid: String?) {
        this.Username = Username
        this.EmailId = EmailId
        this.Mobile = Mobile
        this.ProfilePhoto = ProfilePhoto
        this.CDid = CDid
    }
}