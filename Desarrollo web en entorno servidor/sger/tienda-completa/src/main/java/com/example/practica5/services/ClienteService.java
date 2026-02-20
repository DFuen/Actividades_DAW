package com.example.practica5.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.practica5.dto.ClienteResponseDTO;
import com.example.practica5.exceptions.ClienteNotFoundException;
import com.example.practica5.models.Cliente;
import com.example.practica5.repositories.ClienteRepository;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public List<ClienteResponseDTO> findAll() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteResponseDTO> result = new ArrayList<>(clientes.size());
        for (Cliente cliente : clientes) {
            result.add(new ClienteResponseDTO(
                    cliente.getDni(),
                    cliente.getNombre(),
                    cliente.getApellidos(),
                    cliente.getCorreo(),
                    cliente.getDireccion()));
        }
        return result;
    }

    public ClienteResponseDTO findById(String dni) {
        Cliente cliente = clienteRepository.findById(dni)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente con DNI " + dni + " no encontrado"));
        return new ClienteResponseDTO(
                cliente.getDni(),
                cliente.getNombre(),
                cliente.getApellidos(),
                cliente.getCorreo(),
                cliente.getDireccion());
    }

    public ClienteResponseDTO save(Cliente cliente) {
        Cliente saved = clienteRepository.save(cliente);
        return new ClienteResponseDTO(
                saved.getDni(),
                saved.getNombre(),
                saved.getApellidos(),
                saved.getCorreo(),
                saved.getDireccion());
    }

    public ClienteResponseDTO update(String dni, Cliente cliente) {
        Cliente clienteExistente = clienteRepository.findById(dni)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente con DNI " + dni + " no encontrado"));
        clienteExistente.setNombre(cliente.getNombre());
        clienteExistente.setApellidos(cliente.getApellidos());
        clienteExistente.setCorreo(cliente.getCorreo());
        clienteExistente.setDireccion(cliente.getDireccion());
        Cliente saved = clienteRepository.save(clienteExistente);
        return new ClienteResponseDTO(
            saved.getDni(),
            saved.getNombre(),
            saved.getApellidos(),
            saved.getCorreo(),
            saved.getDireccion());
    }

    public void delete(String dni) {
        clienteRepository.deleteById(dni);
    }

    public ClienteResponseDTO findByCorreo(String correo) {
        Cliente cliente = clienteRepository.findByCorreo(correo)
                .orElseThrow(() -> new ClienteNotFoundException("Cliente con correo " + correo + " no encontrado"));
        return new ClienteResponseDTO(
                cliente.getDni(),
                cliente.getNombre(),
                cliente.getApellidos(),
                cliente.getCorreo(),
                cliente.getDireccion());
    }

    public List<ClienteResponseDTO> findByApellidos(String apellido) {
        List<Cliente> clientes = clienteRepository.findByApellidos(apellido);
        List<ClienteResponseDTO> result = new ArrayList<>(clientes.size());
        for (Cliente cliente : clientes) {
            result.add(new ClienteResponseDTO(
                    cliente.getDni(),
                    cliente.getNombre(),
                    cliente.getApellidos(),
                    cliente.getCorreo(),
                    cliente.getDireccion()));
        }
        return result;
    }

    public Boolean existeCliente(String dni) {
        return clienteRepository.existsById(dni);
    }

    public List<ClienteResponseDTO> findByNombreInicial(String prefijo) {
        List<Cliente> clientes = clienteRepository.findByNombreStartingWithIgnoreCase(prefijo);
        List<ClienteResponseDTO> result = new ArrayList<>(clientes.size());
        for (Cliente cliente : clientes) {
            result.add(new ClienteResponseDTO(
                    cliente.getDni(),
                    cliente.getNombre(),
                    cliente.getApellidos(),
                    cliente.getCorreo(),
                    cliente.getDireccion()));
        }
        return result;
    }

    public Long contarPorNombre(String nombre) {
        return clienteRepository.countByNombreIgnoreCase(nombre);
    }

}
