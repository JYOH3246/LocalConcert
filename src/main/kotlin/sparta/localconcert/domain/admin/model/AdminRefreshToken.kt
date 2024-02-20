package sparta.localconcert.domain.admin.model

import jakarta.persistence.*

@Entity
@Table(name = "tokens")
class AdminRefreshToken(

    @Column(name = "refresh_token")
    var refreshToken: String,

    @ManyToOne
    @JoinColumn(name = "id")
    var admin: Admin,

    ) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tokenid")
    val tokenId: Long? = null

    @Column(name = "reissue_count")
    var reissueCount: Int = 0

    fun updateRefreshToken(refreshToken: String) {
        this.refreshToken = refreshToken
    }

    fun validateRefreshToken(refreshToken: String) = this.refreshToken == refreshToken

    fun increaseReissueCount() {
        reissueCount++
    }

}

