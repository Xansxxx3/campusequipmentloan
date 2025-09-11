package edu.cit.agramon.vicci.campusequimentloan.equipment.Service;

import edu.cit.agramon.vicci.campusequimentloan.equipment.Entity.equipmentEntity;
import edu.cit.agramon.vicci.campusequimentloan.equipment.Repo.equipmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class equipmentService {
    @Autowired
    private equipmentRepo equipmentrepo;

    public equipmentEntity createEquipment(String name, String type, String serialNumber, String availability){

        equipmentEntity equipment = new equipmentEntity();
        equipment.setName(name);
        equipment.setType(type);
        equipment.setSerialNumber(serialNumber);
        equipment.setAvailability(availability);
        return equipmentrepo.save(equipment);
    }

    public List<equipmentEntity> getAllEquipment(){
        return equipmentrepo.findAll();
            }

}
