package iie.ac.cn.site.model;

import java.util.ArrayList;
import java.util.List;

public class ReverseapiExample extends BaseModelExample{
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ReverseapiExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("name like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("name not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andAttributionIsNull() {
            addCriterion("attribution is null");
            return (Criteria) this;
        }

        public Criteria andAttributionIsNotNull() {
            addCriterion("attribution is not null");
            return (Criteria) this;
        }

        public Criteria andAttributionEqualTo(String value) {
            addCriterion("attribution =", value, "attribution");
            return (Criteria) this;
        }

        public Criteria andAttributionNotEqualTo(String value) {
            addCriterion("attribution <>", value, "attribution");
            return (Criteria) this;
        }

        public Criteria andAttributionGreaterThan(String value) {
            addCriterion("attribution >", value, "attribution");
            return (Criteria) this;
        }

        public Criteria andAttributionGreaterThanOrEqualTo(String value) {
            addCriterion("attribution >=", value, "attribution");
            return (Criteria) this;
        }

        public Criteria andAttributionLessThan(String value) {
            addCriterion("attribution <", value, "attribution");
            return (Criteria) this;
        }

        public Criteria andAttributionLessThanOrEqualTo(String value) {
            addCriterion("attribution <=", value, "attribution");
            return (Criteria) this;
        }

        public Criteria andAttributionLike(String value) {
            addCriterion("attribution like", value, "attribution");
            return (Criteria) this;
        }

        public Criteria andAttributionNotLike(String value) {
            addCriterion("attribution not like", value, "attribution");
            return (Criteria) this;
        }

        public Criteria andAttributionIn(List<String> values) {
            addCriterion("attribution in", values, "attribution");
            return (Criteria) this;
        }

        public Criteria andAttributionNotIn(List<String> values) {
            addCriterion("attribution not in", values, "attribution");
            return (Criteria) this;
        }

        public Criteria andAttributionBetween(String value1, String value2) {
            addCriterion("attribution between", value1, value2, "attribution");
            return (Criteria) this;
        }

        public Criteria andAttributionNotBetween(String value1, String value2) {
            addCriterion("attribution not between", value1, value2, "attribution");
            return (Criteria) this;
        }

        public Criteria andApitypeIsNull() {
            addCriterion("apitype is null");
            return (Criteria) this;
        }

        public Criteria andApitypeIsNotNull() {
            addCriterion("apitype is not null");
            return (Criteria) this;
        }

        public Criteria andApitypeEqualTo(String value) {
            addCriterion("apitype =", value, "apitype");
            return (Criteria) this;
        }

        public Criteria andApitypeNotEqualTo(String value) {
            addCriterion("apitype <>", value, "apitype");
            return (Criteria) this;
        }

        public Criteria andApitypeGreaterThan(String value) {
            addCriterion("apitype >", value, "apitype");
            return (Criteria) this;
        }

        public Criteria andApitypeGreaterThanOrEqualTo(String value) {
            addCriterion("apitype >=", value, "apitype");
            return (Criteria) this;
        }

        public Criteria andApitypeLessThan(String value) {
            addCriterion("apitype <", value, "apitype");
            return (Criteria) this;
        }

        public Criteria andApitypeLessThanOrEqualTo(String value) {
            addCriterion("apitype <=", value, "apitype");
            return (Criteria) this;
        }

        public Criteria andApitypeLike(String value) {
            addCriterion("apitype like", value, "apitype");
            return (Criteria) this;
        }

        public Criteria andApitypeNotLike(String value) {
            addCriterion("apitype not like", value, "apitype");
            return (Criteria) this;
        }

        public Criteria andApitypeIn(List<String> values) {
            addCriterion("apitype in", values, "apitype");
            return (Criteria) this;
        }

        public Criteria andApitypeNotIn(List<String> values) {
            addCriterion("apitype not in", values, "apitype");
            return (Criteria) this;
        }

        public Criteria andApitypeBetween(String value1, String value2) {
            addCriterion("apitype between", value1, value2, "apitype");
            return (Criteria) this;
        }

        public Criteria andApitypeNotBetween(String value1, String value2) {
            addCriterion("apitype not between", value1, value2, "apitype");
            return (Criteria) this;
        }

        public Criteria andSearchtypeIsNull() {
            addCriterion("searchtype is null");
            return (Criteria) this;
        }

        public Criteria andSearchtypeIsNotNull() {
            addCriterion("searchtype is not null");
            return (Criteria) this;
        }

        public Criteria andSearchtypeEqualTo(String value) {
            addCriterion("searchtype =", value, "searchtype");
            return (Criteria) this;
        }

        public Criteria andSearchtypeNotEqualTo(String value) {
            addCriterion("searchtype <>", value, "searchtype");
            return (Criteria) this;
        }

        public Criteria andSearchtypeGreaterThan(String value) {
            addCriterion("searchtype >", value, "searchtype");
            return (Criteria) this;
        }

        public Criteria andSearchtypeGreaterThanOrEqualTo(String value) {
            addCriterion("searchtype >=", value, "searchtype");
            return (Criteria) this;
        }

        public Criteria andSearchtypeLessThan(String value) {
            addCriterion("searchtype <", value, "searchtype");
            return (Criteria) this;
        }

        public Criteria andSearchtypeLessThanOrEqualTo(String value) {
            addCriterion("searchtype <=", value, "searchtype");
            return (Criteria) this;
        }

        public Criteria andSearchtypeLike(String value) {
            addCriterion("searchtype like", value, "searchtype");
            return (Criteria) this;
        }

        public Criteria andSearchtypeNotLike(String value) {
            addCriterion("searchtype not like", value, "searchtype");
            return (Criteria) this;
        }

        public Criteria andSearchtypeIn(List<String> values) {
            addCriterion("searchtype in", values, "searchtype");
            return (Criteria) this;
        }

        public Criteria andSearchtypeNotIn(List<String> values) {
            addCriterion("searchtype not in", values, "searchtype");
            return (Criteria) this;
        }

        public Criteria andSearchtypeBetween(String value1, String value2) {
            addCriterion("searchtype between", value1, value2, "searchtype");
            return (Criteria) this;
        }

        public Criteria andSearchtypeNotBetween(String value1, String value2) {
            addCriterion("searchtype not between", value1, value2, "searchtype");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}