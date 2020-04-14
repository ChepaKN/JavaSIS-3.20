package pro.sisit.unit9.data;

import org.springframework.data.repository.CrudRepository;
import pro.sisit.unit9.entity.Buyer;

public interface BuyerRepository extends CrudRepository<Buyer, Long> {

}
