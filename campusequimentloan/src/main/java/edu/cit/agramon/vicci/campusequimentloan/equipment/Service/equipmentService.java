package edu.cit.agramon.vicci.campusequimentloan.equipment.Service;

import edu.cit.agramon.vicci.campusequimentloan.equipment.Entity.equipmentEntity;
import edu.cit.agramon.vicci.campusequimentloan.equipment.Repo.equipmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class equipmentService {
    @Autowired
    private equipmentRepo equipmentrepo;

    public String createEquipment(String name, String type, String serialNumber) {
        // Check if equipment with the same serial number already exists
        Optional<equipmentEntity> existingEquipment = equipmentrepo.findBySerialNumber(serialNumber);

        if (existingEquipment.isPresent()) {
            return "Equipment with serial number " + serialNumber + " already exists.";
        }

        // Create and populate a new equipment entity
        equipmentEntity equipment = new equipmentEntity();
        equipment.setName(name);
        equipment.setType(type);
        equipment.setSerialNumber(serialNumber);
        equipment.setAvailability(true);

        // Save the new equipment entity to the database
        equipmentrepo.save(equipment);

        // Return success message
        return "Equipment successfully created. Name: " + name + ", Type: " + type;
    }


    public List<equipmentEntity> getAllEquipment(){
        return equipmentrepo.findAll();
            }

}
