package com.pablosgon.mortismaycry.webapi.config.converters;

import org.modelmapper.AbstractConverter;

import com.pablosgon.mortismaycry.webapi.models.bs.BSPlayerIcon;

public class PlayerIconToIntegerConverter extends AbstractConverter<BSPlayerIcon, Integer> {

    @Override
    protected Integer convert(BSPlayerIcon icon) {
        return icon.getId();
    }

}