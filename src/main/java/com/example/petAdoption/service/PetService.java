package com.example.petAdoption.service;

import com.example.petAdoption.model.Pet;
import com.example.petAdoption.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.petAdoption.exceptions.PetNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public Optional<Pet> getPetById(Long id) {
        return petRepository.findById(id);
    }

    public List<Pet> getPetsByType(String type) {
        return petRepository.findBytype(type);
    }

    public Pet createPet(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet updatePet(Long id, Pet pet) {
        Pet existingPet = petRepository.findById(id).orElseThrow(() -> new PetNotFoundException("Pet with id " + id + " not found."));

        // Update the fields as per the PATCH request data
        if (pet.getType() != null) {
            existingPet.setType(pet.getType());
        }
        if (pet.getBreed() != null) {
            existingPet.setBreed(pet.getBreed());
        }
        if (pet.getAge() != null) {
            existingPet.setAge(pet.getAge());
        }

        return petRepository.save(existingPet);
    }

    public void deletePet(Long id) {
        if (petRepository.existsById(id)) {
            petRepository.deleteById(id);
        } else {
            throw new PetNotFoundException("Pet with id " + id + " not found.");
        }
    }
}