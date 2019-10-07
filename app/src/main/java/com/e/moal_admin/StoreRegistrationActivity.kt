package com.e.moal_admin


import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_register.*
import net.daum.mf.map.api.MapView
import java.io.IOException
import android.location.Address
import com.google.firebase.database.FirebaseDatabase
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint


class StoreRegistrationActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        var checkTxt: TextView = address_check_txt

        /*주소 등록도 안하고 or 아름 등록 안하고 등록완료 버튼 누를 까봐
        예외처리를 위한 변수 나중에 확인할거면 바꿔줄거니까 var*/
        var isAddressChecked: Boolean = false
        var isNameChecked: Boolean

        /*어떤거 안썼는지 알려줄 텍스트*/
        var filledCheck: TextView = fill_check_txt

        /*가게이름 담는 변수 안변함 어차피*/
        val storeName: TextView = store_name
        val storeAddress: TextView = store_finding

        /*상태바 색상 변경*/
        window.statusBarColor = Color.parseColor("#344955")

        /*daum map view를 띄운다 setDaumMapApiKey가 쓰이진 않지만 안쓰면 Black view에러 남 (업데이트 된지 얼마 안되서 그런가 봄)*/
        val mapView = MapView(this)
        mapView.setDaumMapApiKey("e4b214a56c02f90f1c751c065913ed36")
        val mapViewContainer = map_view
        mapViewContainer.addView(mapView)

        /*검색 버튼을 누르면 입력한 주소 값을 좌표로 바꿔서 맵뷰에 점을 찍는다.
        그리고 맞으면 가게등록을 하라는 텍스트를 지도 밑에 붙여준다.*/

        btn_address_search.setOnClickListener{
            /*어차피 입력한 텍스트가 listener 동작 중 바뀔일 없으니까 val, 마찬가지로 Geocoder도 */
            val geo = Geocoder(this)

            try {
                var Location: List<Address> = geo.getFromLocationName( storeAddress.getText().toString(), 1)

                /*받아온 좌표가 있다면 사이즈가 0보다 클테니까 좌표 찍어줌*/
                if (Location.size > 0) {
                    val store_Lat = Location[0].latitude
                    val store_Lng = Location[0].longitude

                    /*카카오 맵 api 중 포인트 찍기툴 가져옴*/
                    val marker = MapPOIItem()
                    marker.itemName = storeAddress.getText().toString()
                    marker.tag = 0
                    marker.mapPoint = MapPoint.mapPointWithGeoCoord(store_Lat,store_Lng)
                    marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
                    marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                    mapView.addPOIItem(marker)

                    /*카카오 맵 api 중 중심점 변경하고, 줌레밸 변경 한번에*/
                    mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(store_Lat, store_Lng), 1, true)

                    /*주소 등록했으니 이제는 등록버튼 눌러도 됩니다 하고 바꿔주는 변수*/
                    isAddressChecked = true

                    checkTxt.setText("이 위치가 맞나요? 맞으면 등록을 진행해 주세요")
                } else {

                    /*이상한 주소 입력하면 다시 false*/
                    isAddressChecked = false
                    checkTxt.setText("존재하지 않는 주소 입니다. 다시 입력해 주세요")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        /*검색버튼 끝*/

        /*가게등록 버튼 */
        btn_registration.setOnClickListener {

            filledCheck.setText("")

            if(storeName.getText().toString().equals("")){
                isNameChecked = false
            }else{
                isNameChecked = true
            }
            //질문# 이거 어떻게 줄이나요 문법좀여....

            if (!isAddressChecked) {
                if(!isNameChecked){
                    filledCheck.setText("가게이름을 기입하고\n주소를 검색해 주세요")
                }else{
                    filledCheck.setText("주소를 확인해 주세요")
                }
            } else {
                if(!isNameChecked){
                    filledCheck.setText("가게이름을 기입해 주세요")
                }else{
                    filledCheck.setText("")
                    writeNewStore(storeName.getText().toString(), storeAddress.getText().toString(), storeName.getText().toString())
                    toast("DB에 입력합니다.")
                }
            }
        }
    }

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
    }

    private fun writeNewStore (userId: String, address: String, name: String){
        val storeInfo = StoreInfo(address,name)
        database.child(userId).child("StoreInfo").setValue(storeInfo)
    }
}