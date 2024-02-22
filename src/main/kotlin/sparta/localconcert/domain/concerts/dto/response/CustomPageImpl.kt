package sparta.localconcert.domain.concerts.dto.response

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

class CustomPageImpl<T> @JsonCreator(mode = JsonCreator.Mode.PROPERTIES) constructor(
    @JsonProperty("content") content: List<T>,
    @JsonProperty("totalPages") page: Int,
    @JsonProperty("size") size: Int,
    @JsonProperty("totalElements") total: Long
) :
    PageImpl<T>(content, PageRequest.of(page, size), total) {
    @JsonIgnore
    override fun getPageable(): Pageable {
        return super.getPageable()
    }

    @JsonIgnore
    override fun iterator(): MutableIterator<T> {
        return super.iterator()
    }

    @JsonIgnore
    override fun getSort(): Sort {
        return super.getSort()
    }

    @JsonIgnore
    override fun isFirst(): Boolean {
        return super.isFirst()
    }

    @JsonIgnore
    override fun hasPrevious(): Boolean {
        return super.hasPrevious()
    }

    @JsonIgnore
    override fun nextPageable(): Pageable {
        return super.nextPageable()
    }

    @JsonIgnore
    override fun previousPageable(): Pageable {
        return super.previousPageable()
    }

    @JsonIgnore
    override fun isLast(): Boolean {
        return super.isLast()
    }

    @JsonIgnore
    override fun getNumber(): Int {
        return super.getNumber()
    }

    @JsonIgnore
    override fun getNumberOfElements(): Int {
        return super.getNumberOfElements()
    }

    @JsonIgnore
    override fun isEmpty(): Boolean {
        return super.isEmpty()
    }
}



