package com.example.gym23.FormData

import com.example.gym23.Entity.*

class UserSignByNum(
    private var tel:String,
) {
    fun validateTel(): ValidationResult {
        val telPattern = "^\\d{10}$"

        return if(tel.isEmpty()){
            ValidationResult.Empty("Please Enter Number")
        }else if(!tel.matches(telPattern.toRegex())){
            ValidationResult.Invalid("Please Enter Valid Number")
        }else{
            ValidationResult.Valid
        }
    }
}