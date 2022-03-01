package com.example.smartpharm.models

object ConverterMedicationClasses {

    fun toMyMedication(med:MedicationDB):MyMedications{
        return MyMedications(
            idMedication = med.idMedication,
            idUser = med.idUser,
            photo = med.photo,
            Days = med.Days,
            Name=med.Name,
            description = med.description,
            first_day = mapOf("day" to med.first_day_Day, "month" to med.first_day_Month, "year" to med.first_day_Year) as MutableMap<String, Int>,
            Dinner = mapOf("hour" to med.DinnerHour, "minute" to med.DinnerMinute) as MutableMap<String, Int>,
            Launch = mapOf("hour" to med.LaunchHour, "minute" to med.LaunchMinute) as MutableMap<String, Int>
        )
    }

    fun toMedicationDB(med:MyMedications):MedicationDB{
        return MedicationDB(
            idMedication = med.idMedication,
            idUser = med.idUser,
            photo = med.photo,
            Days = med.Days,
            Name=med.Name,
            description = med.description,
            DinnerHour = med.Dinner!!["hour"]!!,
            DinnerMinute = med.Dinner!!["minute"]!!,
            LaunchHour = med.Launch!!["hour"]!!,
            LaunchMinute = med.Launch!!["minute"]!!,
            first_day_Day = med.first_day!!["day"]!!,
            first_day_Month = med.first_day!!["month"]!!,
            first_day_Year = med.first_day!!["year"]!!,
        )
    }
}