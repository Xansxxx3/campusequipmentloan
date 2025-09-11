package edu.cit.agramon.vicci.campusequimentloan.equipment.Controller;
import edu.cit.agramon.vicci.campusequimentloan.equipment.Entity.equipmentEntity;
import edu.cit.agramon.vicci.campusequimentloan.equipment.Service.equipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/equipment")
public class equipmentController {

    @Autowired
    equipmentService equipmentservice;

        @PutMapping("/add")
        public equipmentEntity createEquipment(@RequestBody equipmentEntity equipment){
            return equipmentservice.createEquipment(equipment.getName(), equipment.getType(), equipment.getSerialNumber(), equipment.getAvailability());
        }

}
