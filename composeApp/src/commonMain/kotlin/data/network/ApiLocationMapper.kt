package models

import data.Location
import data.Mapper

class ApiLocationMapper : Mapper<ApiLocation, Location>() {
    override fun map(model: ApiLocation): Location {
        return Location(
            id = model.id,
            name = model.name,
            type = model.type,
            dimension = model.dimension,
            residents = model.residents,
            url = model.url
        )
    }

    override fun inverseMap(model: Location): ApiLocation {
        TODO("Not yet implemented")
    }
}