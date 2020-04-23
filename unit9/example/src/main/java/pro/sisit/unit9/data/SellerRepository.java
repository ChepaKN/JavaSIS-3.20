package pro.sisit.unit9.data;

import org.springframework.data.repository.CrudRepository;
import pro.sisit.unit9.entity.Seller;

public interface SellerRepository extends CrudRepository<Seller, Long> {
}
