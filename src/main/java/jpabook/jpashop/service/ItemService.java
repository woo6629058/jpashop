package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /**
     * 변경 감지(dirty checking) - 연속성 컨텍스트가 자동 변경
     * 컨트롤러에서 어설프게 엔티티를 생성하지 않아야 한다.
     * 트랜잭션이 있는 서비스 계층에 식별자(`id`)와 변경할 데이터를 명확하게 전달해야 한다.(파라미터 or dto)
     * 트랜잭션이 있는 서비스 계층에서 영속 상태의 엔티티를 조회하고, 엔티티의 데이터를 직접 변경해야 한다.
     * 트랜잭션 커밋 시점에 변경 감지가 실행된다.
     */
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) { // 파리미터로 넘어온 준영속 상태의 엔티티

        Item findItem = itemRepository.findOne(itemId); // 같은 엔티티를 조회
        findItem.setName(name); // 데이터 수정
        findItem.setPrice(price); // 데이터 수정
        findItem.setStockQuantity(stockQuantity); // 데이터 수정
    }

    public List<Item> findItems() {
         return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
