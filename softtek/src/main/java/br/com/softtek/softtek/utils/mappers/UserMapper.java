package br.com.softtek.softtek.utils.mappers;

import br.com.softtek.softtek.adapters.outbound.jpa.entities.MongoUserEntity;
import br.com.softtek.softtek.domain.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toDomain(MongoUserEntity entity);

    MongoUserEntity toEntity(User user);
}
