package com.example.animal_shelter.animal_shelter.model;

public enum TypesDocumentDog {
    DOG_DATING_RULES("Правила знакомства с собакой до того, как можно забрать ее из приюта"),
    DOCUMENTS_TO_ADOPT_DOG("Документы, необходимые для того, чтобы взять собаку из приюта"),
    SHIPPING_RECOMMENDATIONS ("Рекомендации по транспортировке животного."),
    PUPPY_HOME_IMPROVEMENT_TIPES("Рекомендации по обустройству дома для щенка"),
    DOG_HOME_IMPROVEMENT_TIPES("Рекомендации по обустройству дома для взрослой собаки"),
    DOG_WITH_DISABILITY_HOME_IMPROVEMENT_TIPES("Рекомендации по обустройству дома для собаки с ограниченными возможностями (зрение, передвижение)"),
    CYNOLOG_ADVIVCE("Советы кинолога по первичному общению с собакой."),
    REASONS_FOR_REJECTION("Причины, почему могут отказать и не дать забрать собаку из приюта");

    private String title;

    TypesDocumentDog(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

//    public static TypesDocumentDog fromCode(String typesDocumentDog) {
//        if (typesDocumentDog == null) {
//            return null;
//        }
//        return switch (typesDocumentDog) {
//            case "DOG_DATING_RULES", "男" -> TypesDocumentDog.DOG_DATING_RULES;
//           // case "FEMALE", "女" -> Gender.FEMALE;
//            default -> throw new IllegalArgumentException("Invaild input value");
//        };
//    }
}

