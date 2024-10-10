package demo.jjboard.repository.custom;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import demo.jjboard.controller.form.BoardCond;
import demo.jjboard.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static demo.jjboard.entity.QBoard.*;
import static demo.jjboard.entity.QMember.*;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Board> findBoardByCond(BoardCond cond) {
        return queryFactory.selectFrom(board)
                .leftJoin(board.member,member)
                .where(nameFind(cond.getName()),
                        hitCountFind(cond.getHitCount()),
                        recommendFind(cond.getRecommendation()),
                        notRecommendFind(cond.getNotRecommendation()),
                        memberNameFind(cond.getMemberName()))
                .fetch();
    }

    private BooleanExpression memberNameFind(String memberName) {
        return memberName != null && !memberName.trim().isEmpty() ? board.member.username.like("%"+memberName+"%") : null;
    }

    private BooleanExpression notRecommendFind(Integer notRecommendation) {
        return notRecommendation != null ? board.notRecommendation.loe(notRecommendation) : null;
    }

    private BooleanExpression recommendFind(Integer recommendation) {
        return recommendation != null ? board.recommendation.goe(recommendation) : null;
    }

    private BooleanExpression hitCountFind(Integer hitCount) {
        return hitCount != null ? board.boardHitCount.goe(hitCount) : null;
    }

    private BooleanExpression nameFind(String name) {
        return name != null && !name.trim().isEmpty() ? board.boardTitle.like("%"+name+"%") : null;
    }
}
