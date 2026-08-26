//
//List<File> = Unirest.post("https://api.wali.chat/v1/files")
//        .header("Token", "ea52b1bb3143b11da3faf3a802820c83276c2f7dc47d9acd30ac09e5736bcdb52ba46f8f3fe4e846")
//        .field("file", new File("/path/to/image.jpg"))
//        .getBody()

//    private void sendOtpFirst() {
//
//
//        String nomorTelfon = "+62" + dataMemberSementara.getNomorMembership().substring(1);
//        String randomOtp = new DecimalFormat("0000").format(new Random().nextInt(9999));
//
//        String pesanOTP = "KODE OTP TRANSAKSI BELANJA DENGAN"+
//                " SALES : " + sessionManager.getKeyName() +
//                " DAN MEMBER : " + dataMemberSementara.getNamaMembership() +
//                " ADALAH\n\n" + randomOtp + "\n\nSILAHKAN SEGERA BERITAHU SALES TERKAIT KODE OTP ANDA !";
//
//
//        PostBodyOTP postBodyOTP = new PostBodyOTP(
//                nomorTelfon,
//                pesanOTP
//        );
//
//        final ProgressDialog progressDialog = ProgressDialog.show(SetupOTPMember.this, "Loading", "Setup OTP ...");
//        APIInterface apiInterface = APIClient.getClient(ApiOTP.urlOTP).create(APIInterface.class);
//        Call<ResponseSendOTP> call = apiInterface.doSendOTP(
//                "application/json",
//                ApiOTP.OTP_SECRET_KEY,
//                postBodyOTP
//        );
//
//        call.enqueue(new Callback<ResponseSendOTP>() {
//            @Override
//            public void onResponse(Call<ResponseSendOTP> call, Response<ResponseSendOTP> response) {
//                try {
//                    progressDialog.dismiss();
//                    if (response != null) {
//
//                        ResponseSendOTP responseSendOTP = response.body();
//                        Toast.makeText(SetupOTPMember.this, "Pengiriman OTP Berhasil, tunggu member memberikan OTP ke Anda", Toast.LENGTH_LONG).show();
//
//                        final Calendar expOTP = Calendar.getInstance();
//                        expOTP.add(Calendar.MINUTE, 1);
//                        Date deadlineOTP = expOTP.getTime();
//
//                        String deadlenOTP = formatExp.format(deadlineOTP);
//
//                        sessionManager.setOtp(
//                                true,
//                                randomOtp,
//                                deadlenOTP
//                        );
//
//                        setupView();
//
//
//                    }
//                } catch (Exception e) {
//                    Log.e(TAG, "onResponse: " + e.getMessage());
//                    Toast.makeText(SetupOTPMember.this, "Error kirim OTP !", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseSendOTP> call, Throwable t) {
//                progressDialog.dismiss();
//                Log.e(TAG, "onResponse: " + t.getMessage());
//                Toast.makeText(SetupOTPMember.this, "Error Kirim OTP", Toast.LENGTH_SHORT).show();
//                finish();
//            }
//        });
//
//    }