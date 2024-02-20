package sparta.localconcert.domain.admin.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "admins")
class Admin(

    @Column(name = "email")
    var email: String,

    @Column(name = "password")
    var password: String,

    @Column(name = "created_at")
    var createAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updateAt: LocalDateTime = LocalDateTime.now()
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    val id: Long? = null

}