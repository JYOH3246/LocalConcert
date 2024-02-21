package sparta.localconcert.domain.concerts.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import sparta.localconcert.global.entity.BaseTimeEntity
import java.time.LocalDate
import java.time.LocalTime


@Entity
@Table(name = "concerts")
class Concert(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(name = "title")
    var title: String,

    @Column(name = "location")
    var location: String,

    @Column(name = "contents")
    var contents: String,

    @Column(name = "price")
    var price: String,

    @Column(name = "ratings")
    var ratings: String,

    @Column(name = "pageURL")
    var pageUrl: String,

    @Column(name = "ticketURL")
    var ticketUrl: String,

    @Column(name = "isPriced")
    var isPriced: Boolean,

    @Column(name = "isDeleted")
    var isDeleted: Boolean = false,

    @Column(name = "searches")
    var searches: Long = 0,

    @Column(name = "host")
    var host: String,

    @Column(name = "organizer")
    var organizer: String,

    @Column(name = "notice")
    var notice: String,

    @Column(name = "address")
    var address: String,

    @Column(name = "startDate")
    var startDate: LocalDate,

    @Column(name = "endDate")
    var endDate: LocalDate,

    @Column(name = "startTime")
    var startTime: LocalTime,

    @Column(name = "endTime")
    var endTime: LocalTime,

//    @ManyToOne
//    @JoinColumn(name = "admin_id")
//    var adminId: Admin,

) : BaseTimeEntity() {

}