package org.springframework.samples.petclinic.pet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PetsController {
    @Autowired
    PetsRepository petsRepository;
    private final Logger logger = LoggerFactory.getLogger(PetsController.class);

    
    /** 
     * @param pet
     * @return String
     */
    @PostMapping("/pets/new")
    public String SavePet(@RequestBody Pets pet){
        petsRepository.save(pet);
        return "New Pet: " + pet.getName() +" saved";
    }
    @GetMapping("/pets")
    public List<Pets> getAllPets(){
        logger.info("entered into controller layer");
        List<Pets> result = petsRepository.findAll();
        logger.info("Number of records fetchted: " + result.size());
        return result;
    }
    @GetMapping("/pets/create")
    public String createHardCodedData(){
        Pets p1 = new Pets(1, "Molly", "dog");
        Pets p2 = new Pets(2, "Greg", "cat");
        Pets p3 = new Pets(3, "Sally", "salamander");
        petsRepository.save(p1);
        petsRepository.save(p2);
        petsRepository.save(p3);
        return "Successfully saved pets";
    }
    @GetMapping("pets/{petId}")
    public Optional<Pets> getPetById(@PathVariable("petId") int id){
        logger.info("Entered into Controller Layer");
        return petsRepository.findById(id);
    }
    @DeleteMapping("/pets/{id}")
        public Optional<Pets> deletePetById(@PathVariable("id") int id){
            Optional<Pets> pet = petsRepository.findById(id);
            petsRepository.deleteById(id);
            return pet;
        }
    }
