package com.e.moal_admin


import android.content.Context
import android.content.Intent
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
import android.util.Log
import com.google.firebase.database.FirebaseDatabase
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


class StoreRegistrationActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance().reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val intent = Intent(this, MainActivity::class.java)

        /*주소에대한 위치가 맞는지 물어보고 맞으면 등록하거나 아니면 그런 주소 없다고 함*/
        var checkTxt: TextView = address_check_txt

        /*주소 등록도 안하고 or 아름 등록 안하고 등록완료 버튼 누를 까봐
        예외처리를 위한 변수 나중에 확인할거면 바꿔줄거니까 var*/
        var isAddressChecked: Boolean = false
        var isNameChecked: Boolean

        /*어떤거 안썼는지 알려줄 텍스트*/
        var filledCheck: TextView = fill_check_txt

        /*가게이름 담는 변수 안변함 어차피*/
        val storeName: EditText = store_name
        val storeAddress: TextView = store_finding

        /*가게 좌표담는 double형 숫자 두개*/
        var storeLat: Double
        var storeLng: Double
        var Location: List<Address>

        /*상태바 색상 변경*/
        window.statusBarColor = Color.parseColor("#344955")

        /*daum map view를 띄운다 setDaumMapApiKey가 쓰이진 않지만 안쓰면 Black view에러 남 (업데이트 된지 얼마 안되서 그런가 봄)
        데스크탑에서 코드 업로드 할 경우 네이티브 앱 키 = e4b214a56c02f90f1c751c065913ed36 노트북: e4b214a56c02f90f1c751c065913ed36*/
        val mapView = MapView(this)
        mapView.setDaumMapApiKey("5a066a8885477fc248bead6144c637b0")
        val mapViewContainer = map_view
        mapViewContainer.addView(mapView)

        /*주소변환에 필요한 Geocoder*/
        val geo = Geocoder(this)


        /*3차로 추가한 기능임-> 키보드에서 검색버튼으로 접근해서 검색 누를 수 있도록 함*/
        storeName.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    btn_store_search.performClick()
                    closeKeyboard()
                }
            }
            true
        })

        storeAddress.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    btn_address_search.performClick()
                    closeKeyboard()
                }
            }
            true
        })

        /*2차로 추가한 기능임 -> 가게이름을 쳤을때 위도 경도로 바꿔주고, 이를 다시 주소로 변환해서
        바로 주소입력 없더라도 확인하고 등록할 수 있도록 하는 기능을 넣어볼까 함*/

        btn_store_search.setOnClickListener {
            try {
                Location = geo.getFromLocationName( storeName.getText().toString(), 1)

                /*받아온 좌표가 있다면 사이즈가 0보다 클테니까 좌표 찍어줌*/
                if (Location.size > 0) {
                    storeLat = Location[0].latitude
                    storeLng = Location[0].longitude
                    /*다시 주소정보로 바꿔주자*/
                    try{
                        Location = geo.getFromLocation( storeLat,storeLng,10)
                        /*잘 불려왔고*/
                        if(Location!=null){
                            /*그주소가 있다면*/
                            if(Location.size==0) {
                                checkTxt.setText("자동으로 주소를 변환할 수 없습니다.\n수동으로 주소를 입력해주세요.")
                            }else{
                                storeAddress.setText(Location.get(0).getAddressLine(0).toString().trim())
                                btn_address_search.performClick()
                            }
                        }
                    }catch (e: IOException){
                        Log.d("StoreRegistrationActivity","서버에서 주소 바뀌다 터짐")
                    }
                } else {
                    checkTxt.setText("자동으로 주소를 변환할 수 없습니다.\n수동으로 주소를 입력해주세요.")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            closeKeyboard()
        }

        /*검색 버튼을 누르면 입력한 주소 값을 좌표로 바꿔서 맵뷰에 점을 찍는다.
        그리고 맞으면 가게등록을 하라는 텍스트를 지도 밑에 붙여준다.*/

        btn_address_search.setOnClickListener{
            /*어차피 입력한 텍스트가 listener 동작 중 바뀔일 없으니까 val, 마찬가지로 Geocoder도 */
            try {
                Location = geo.getFromLocationName( storeAddress.getText().toString(), 1)

                /*받아온 좌표가 있다면 사이즈가 0보다 클테니까 좌표 찍어줌*/
                if (Location.size > 0) {
                    storeLat = Location[0].latitude
                    storeLng = Location[0].longitude

                    /*카카오 맵 api 중 포인트 찍기툴 가져옴*/
                    val marker = MapPOIItem()
                    marker.itemName = storeAddress.getText().toString()
                    marker.tag = 0
                    marker.mapPoint = MapPoint.mapPointWithGeoCoord(storeLat,storeLng)
                    marker.markerType = MapPOIItem.MarkerType.BluePin // 기본으로 제공하는 BluePin 마커 모양.
                    marker.selectedMarkerType = MapPOIItem.MarkerType.RedPin // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                    mapView.addPOIItem(marker)

                    /*카카오 맵 api 중 중심점 변경하고, 줌레밸 변경 한번에*/
                    mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(storeLat, storeLng), 1, true)

                    /*주소 등록했으니 이제는 등록버튼 눌러도 됩니다 하고 바꿔주는 변수*/
                    isAddressChecked = true

                    checkTxt.setText("이 위치가 맞나요? 맞으면 등록을 진행해 주세요\n아니라면 주소를 수정하시고 다시 검색해 주세요")
                } else {

                    /*이상한 주소 입력하면 다시 false*/
                    isAddressChecked = false
                    checkTxt.setText("존재하지 않는 주소 입니다. 다시 입력해 주세요\n아니라면 주소를 수정하시고 다시 검색해 주세요")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
            closeKeyboard()
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
                    writeNewStore(storeName.getText().toString().trim(), storeAddress.getText().toString().trim(), storeName.getText().toString().trim())
                    toast("주소등록이 완료되었습니다.")
                    startActivity(intent)
                }
            }
        }
    }
    /*onCreate 끝*/

    private fun toast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    /*어느 상황에서든 키보드를 내려주는 함수*/
    private fun closeKeyboard() {
        var view = this.currentFocus
        if(view != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun writeNewStore (userId: String, address: String, name: String){
        val storeInfo = StoreInfo(address,name)
        database.child(userId).child("StoreInfo").setValue(storeInfo)
    }
}