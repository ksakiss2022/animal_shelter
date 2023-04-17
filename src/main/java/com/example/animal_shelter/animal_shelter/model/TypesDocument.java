package com.example.animal_shelter.animal_shelter.model;

public  enum TypesDocument {
    ANIMAL_DATING_RULES("Правила знакомства с животным до того, как можно забрать ее из приюта"),
    DOCUMENTS_TO_ADOPT_ANIMAL("Документы, необходимые для того, чтобы взять животное из приюта"),
    SHIPPING_RECOMMENDATIONS ("Рекомендации по транспортировке животного"),
    PUPPY_KITTEN_HOME_IMPROVEMENT_TIPES("Рекомендации по обустройству дома для щенка/котенка"),
    ANIMAL_HOME_IMPROVEMENT_TIPES("Рекомендации по обустройству дома для взрослого животного"),
    ANIMAL_WITH_DISABILITY_HOME_IMPROVEMENT_TIPES("Рекомендации по обустройству дома для животного с ограниченными возможностями (зрение, передвижение)"),
    CYNOLOG_ADVIVCE("Советы кинолога по первичному общению с собакой."),
    REASONS_FOR_REJECTION("Причины, почему могут отказать и не дать забрать собаку из приюта");

    private String title;

    TypesDocument(String title) {
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

