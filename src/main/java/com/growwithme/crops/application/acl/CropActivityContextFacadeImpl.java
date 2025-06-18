package com.growwithme.crops.application.acl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import com.growwithme.crops.domain.model.aggregates.CropActivity;
import com.growwithme.crops.domain.model.commands.DeleteAllCropActivitiesByCropIdCommand;
import com.growwithme.crops.domain.model.commands.DeleteCropActivityCommand;
import com.growwithme.crops.domain.model.queries.GetAllCropActivitiesByCropIdQuery;
import com.growwithme.crops.domain.services.CropActivityCommandService;
import com.growwithme.crops.domain.services.CropActivityQueryService;
import com.growwithme.crops.interfaces.acl.CropActivityContextFacade;
import java.util.List;

@Service
@AllArgsConstructor
public class CropActivityContextFacadeImpl implements CropActivityContextFacade {
    private final CropActivityCommandService commandService;
    private final CropActivityQueryService queryService;

    @Override
    public void deleteCropActivity(Long id) {
        commandService.handle(new DeleteCropActivityCommand(id));
    }

    @Override
    public void deleteAllCropActivitiesByCropId(Long cropId) {
        commandService.handle(new DeleteAllCropActivitiesByCropIdCommand(cropId));
    }

    @Override
    public List<CropActivity> fetchAllCropActivitiesByCropId(Long cropId) {
        return queryService.handle(new GetAllCropActivitiesByCropIdQuery(cropId));
    }
}
