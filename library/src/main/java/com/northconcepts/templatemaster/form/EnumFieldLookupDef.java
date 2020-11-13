package com.northconcepts.templatemaster.form;

public class EnumFieldLookupDef extends FieldLookupDef {

    private final Enum<? extends Enum<?>>[] enumConstants;

    public <E extends Enum<E>> EnumFieldLookupDef(Class<E> enumType) {
        this.enumConstants = enumType.getEnumConstants();
    }

    @Override
    public int getValueCount() {
        return enumConstants.length;
    }

    @Override
    public String getValue(int index) {
        return enumConstants[index].name();
    }
    
    @Override
    public String getDisplayName(int index) {
        return enumConstants[index].toString();
    }

}
