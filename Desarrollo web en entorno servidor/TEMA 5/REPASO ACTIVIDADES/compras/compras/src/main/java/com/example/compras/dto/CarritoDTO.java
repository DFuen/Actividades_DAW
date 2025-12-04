package com.example.compras.dto;

import java.util.List;

public class CarritoDTO {

    private String clienteId;
    private List<ItemDTO> items;

    public CarritoDTO(String clienteId, List<ItemDTO> items) {
        this.clienteId = clienteId;
        this.items = items;
    }

    public CarritoDTO() {
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

}
