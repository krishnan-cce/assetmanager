package com.udyata.linentrack.linentrack.serviceimpl.serialnumber;

import com.udyata.linentrack.linentrack.entity.serialnumbermaster.SerialNumberMaster;
import com.udyata.linentrack.linentrack.exception.ResourceNotFoundException;
import com.udyata.linentrack.linentrack.repository.serialnumbermaster.SerialNumberMasterRepository;
import com.udyata.linentrack.linentrack.service.serialnumber.SerialNumberService;
import com.udyata.linentrack.linentrack.utils.CommonUtil;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

@Service
public class SerialNumberServiceImpl implements SerialNumberService {

    private final SerialNumberMasterRepository serialNumberMasterRepository;

    public SerialNumberServiceImpl(SerialNumberMasterRepository serialNumberMasterRepository) {
        this.serialNumberMasterRepository = serialNumberMasterRepository;
    }

    @Override
    @Transactional
    public String[] generateNextSerialNumber(String formName) {
        SerialNumberMaster serialNumberMaster = serialNumberMasterRepository.findByForm(formName)
                .orElseThrow(() -> new ResourceNotFoundException("Serial Number Not Found ", formName, 0));
        String nextSerialNumber = CommonUtil.geneNextTransNo(serialNumberMaster.getCode());
        String completeSerialNumber = serialNumberMaster.getPrefix() + nextSerialNumber;
        return new String[]{nextSerialNumber, completeSerialNumber};
    }

    @Override
    @Transactional
    public void updateSerialNumber(String formName, String nextSerialNumber) {
        SerialNumberMaster serialNumberMaster = serialNumberMasterRepository.findByForm(formName)
                .orElseThrow(() -> new ResourceNotFoundException("Serial Number Not Found ", formName, 0));
        serialNumberMaster.setCode(nextSerialNumber);
        serialNumberMasterRepository.save(serialNumberMaster);
    }
}
