package io.e4i2.repository;

import io.e4i2.entity.ChattingMessage;
import org.springframework.data.repository.CrudRepository;

public interface ChattingMessageRepository extends CrudRepository<ChattingMessage, Integer> {
}
