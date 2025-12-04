package com.example.Compra_Periodos.dto;

import java.util.List;

public class CarritoDTO {
    private String clienteId;
    private List<ItemDTO> items;

    public CarritoDTO() {
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }
}
