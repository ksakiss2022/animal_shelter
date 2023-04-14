package com.example.animal_shelter.animal_shelter.model;

public enum BotState {
    SHELTER_INFORMATION("shelter_information"),
    SHELTER_TELL("shelter_tell"),
    SHELTER_SCHEDULE("shelter_schedule"),
    SHELTER_ADDRESS("shelter_address"),
    SHELTER_LOCATION_MAP("shelter_location_map"),
    CALL_VOLUNTEER("call_volunteer"),
    SAFETY_RECOMMENDATIONS("safety_recommendations"),
    ACCEPT_RECORD_CONTACT("accept_record_contact"),
    ADOPT_DOC_FROM_SHELTER("adopt_doc_from_shelter"),
    SEND_PET_REPORT("send_pet_report"),
    DOG_DATING_RULES("DOG_DATING_RULES"),
    DOCUMENTS_TO_ADOPT_DOG("DOCUMENTS_TO_ADOPT_DOG"),
    SHIPPING_RECOMMENDATIONS ("SHIPPING_RECOMMENDATIONS"),
    PUPPY_HOME_IMPROVEMENT_TIPES("PUPPY_HOME_IMPROVEMENT_TIPES"),
    DOG_HOME_IMPROVEMENT_TIPES("DOG_HOME_IMPROVEMENT_TIPES"),
    DOG_WITH_DISABILITY_HOME_IMPROVEMENT_TIPES("DOG_WITH_DISABILITY_HOME_IMPROVEMENT_TIPES"),
    CYNOLOG_ADVIVCE("CYNOLOG_ADVIVCE"),
    CYNOLOGISTS("CYNOLOGISTS"),

    REASONS_FOR_REJECTION("REASONS_FOR_REJECTION");
    ;


    private String title;

    BotState(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}