package com.example.nutshop

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.nutshop.databinding.ActivityMainBinding
import com.google.android.gms.wallet.PaymentsClient
import com.google.android.gms.wallet.Wallet
import com.google.android.gms.wallet.WalletConstants
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navHost: NavHostFragment

    private lateinit var paymentsClients : PaymentsClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val walletOptions = Wallet.WalletOptions.Builder().setEnvironment(WalletConstants.ENVIRONMENT_TEST).build()
        paymentsClients = Wallet.getPaymentsClient(this, walletOptions)



        navHost =
            supportFragmentManager.findFragmentById(R.id.fragment_container_for_fragments) as NavHostFragment




        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home_page -> {
                    navHost.findNavController().navigate(R.id.home_page)
                    true
                }
                R.id.cart_page -> {
                    navHost.findNavController().navigate(R.id.cart_page)
                    true
                }
                R.id.favorite_page -> {
                    navHost.findNavController().navigate(R.id.favoriteFragment)
                    true
                }
                R.id.account_page -> {
                    if (Firebase.auth.currentUser != null) {
                        navHost.findNavController().navigate(R.id.profileFragment)
                    } else {
                        navHost.findNavController().navigate(R.id.loginFragment)
                    }
                    true
                }
                else -> false
            }
        }

        navHost.findNavController()
            .addOnDestinationChangedListener(NavController.OnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.searchFragment || destination.id == R.id.productDetailFragment) {
                    binding.bottomNavigationView.visibility = View.GONE
                } else binding.bottomNavigationView.visibility = View.VISIBLE
            })


    }

    override fun onSupportNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onNavigateUp()
    }

    override fun onBackPressed() {
        navHost.findNavController().navigateUp()
        super.onBackPressed()
    }



    /* fun setData() {
         val product1 = Product(
             productName = "OPTIMUM NUTRITION GOLD STANDARD 100% WHEY PROTEIN",
             price = 39.99,
             quantity = 56,
             description = "Each serving of the world’s best-selling whey protein powder provides 24 grams of high-quality whey protein primarily from Whey Protein Isolate, which has had excess carbohydrates, fat, and lactose ‘isolated’ out using sophisticated filtering technologies. The powder is also instantized for easy mixing using just a glass and spoon. With more than 20 different flavors – including naturally flavored options – there’s no doubt this is the GOLD STANDARD®.",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FOPT289_grey_480x.webp?alt=media&token=fdf17054-fa84-4e41-9f2d-3e8c3fa524cf",
             category = Category.WHEYPROTEIN
         )

         val product2 = Product(
             productName = "BODYBUILDING.COM SIGNATURE 100% WHEY PROTEIN POWDER",
             price = 59.99,
             quantity = 23,
             description = "Our Signature 100% Whey Protein combines three different sources of whey protein in one powerful blend: whey protein concentrate, whey protein isolate, and hydrolyzed whey protein. Whey protein is the most important weapon in your muscle-building arsenal. It promotes muscle growth, counteracts muscle breakdown, and supports your workout recovery.*\n" +
                     "\n" +
                     "Signature 100% Whey Protein is perfect first thing in the morning, between meals, and especially after your workout. It’s so delicious it’ll can slay your sweet tooth without wrecking your macros. It’s a fantastic combination of value and quality. While many products are primarily whey protein concentrate, Signature 100% Whey has an impressive profile of 13 grams of isolate and 6 grams each of hydrolysate and concentrate. That means you have a lot of clean, quick-digesting, low-carb protein, with just enough whey concentrate for a creamy flavor and texture.\n" +
                     "\n" +
                     "Simply put, it tastes really good. In reviews, customers rave about the delicious flavors with no artificial aftertaste. Mocha cappuccino and seasonal favorite oatmeal cookie are the most-praised flavors.\n" +
                     "\n" +
                     "It’s a versatile whey blend that can not only be easily mixed in a shaker bottle, but also added to a wide variety of recipes including waffles, cookies, and more. Many reviewers also find this product is easier to stomach than some of its competitors, likely due to the balanced protein blend.",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FBBCOM_NEW_Signature_Whey_OC_5LB_1123_grey_900x.webp?alt=media&token=c91b888a-befe-4268-ad67-bfe90ce43797",
             category = Category.WHEYPROTEIN
         )

         val product3 = Product(
             productName = "JYM SUPPLEMENT SCIENCE PRO JYM PROTEIN POWDER",
             price = 39.99,
             quantity = 14,
             description = "Pro JYM is a premium protein powder, an ideal blend of the three most effective types of proteins for building muscle: whey, micellar casein, and egg protein. In synergy with Pre JYM and Post JYM, Pro JYM is your go-to protein both before and after intense workouts. Pro JYM is also the most delicious protein on the market!",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FBBCOM_NEW_Signature_Whey_OC_5LB_1123_grey_900x.webp?alt=media&token=c91b888a-befe-4268-ad67-bfe90ce43797",
             category = Category.WHEYPROTEIN
         )

         val product4 = Product(
             productName = "DYMATIZE ISO100 HYDROLYZED WHEY PROTEIN ISOLATE",
             price = 39.99,
             quantity = 2,
             description = "ISO100 is simply muscle-building fuel. If your goal is gaining muscle size and strength, then ISO100 is your perfect workout partner. Loaded with muscle-building amino acids, ISO100 can support even the most serious resistance-training programs.",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2F5lb-Cinn-Bun-DYM3930008-6721_grey_900x.webp?alt=media&token=a5c5df74-259e-4f24-93c6-76bcecb0573e",
             category = Category.WHEYPROTEIN
         )

         val product5 = Product(
             productName = "BODYBUILDING.COM SIGNATURE PRE WORKOUT",
             price = 26.24,
             quantity = 180,
             description = "Pre-Workout includes proven ingredients that promote intense pump and growth while battling fatigue, so you can work out harder and longer. With 200mg of caffeine, this is perfect for high volume lifts, cross-fit, cardio or intense strength training. Taking a mega dose of stimulants isn’t always the best before your training so we focused this formulation on high quality, proven, efficacious ingredients to help you hit your training goals and make every workout count. Using this formula before training will help provide energy and get you focused to get the most from your workout.*\n" +
                     "\n" +
                     "This powerhouse formula drives both results and a great value and is loaded with proven ingredients to help power your workouts:\n" +
                     "\n" +
                     "L-CITRULLINE\n" +
                     "One of the keys to a great workout is a great pump. The amino acid L-Citrulline contributes to your body’s production of nitric oxide, a vasodilator. When your blood vessels are dilated, your body is more effectively able to circulate oxygen and other performance-maximizing nutrients to working muscles.*\n" +
                     "\n" +
                     "L-LEUCINE\n" +
                     "The amino acid L-Leucine is known as a trigger for muscle protein synthesis (aka muscle growth) and a fuel source for your hard-lifting muscles. Your body cannot make L-Leucine, so it must come from supplements or food.*\n" +
                     "\n" +
                     "CARNOSYN® BETA-ALANINE\n" +
                     "Beta-alanine acts as a buffer between fatigue-promoting lactic acid and your muscles by producing carnosine, which keeps lactic acid in check during intense workouts. That means better muscular endurance, more energy for high-intensity or explosive movements, and better overall workout performance.*\n" +
                     "\n" +
                     "CAFFEINE\n" +
                     "Caffeine is one of the most common pre-workout ingredients because it works. Supplementing with caffeine has been shown to increase focus and improve workout performance, allowing you to dig in and get the work done.*",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FBBCOM_NEW_Signature_PRE_FP_V8_grey_900x.webp?alt=media&token=3d475173-dd40-4dc9-a45e-908f56ce126c",
             category = Category.PREWORKOUT
         )

         val product6 = Product(
             productName = "EVLUTION NUTRITION BCAA ENERGY AMINO ACIDS",
             price = 20.79,
             quantity = 78,
             description = "BCAA ENERGY is the best-selling, naturally energized Branched Chain Amino Acid drink that offers all-in-one energy, focus, muscle building and recovery support. BCAA ENERGY boosts performance by combining 5 grams of Branched Chain Amino Acids, 1.5 grams of Endurance Boosting Amino Acids, Natural Energizers, Antioxidants, and B Vitamins to power your day whether you’re in the gym, on the field, at school or the office!",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FEVL5390013_grey_900x.webp?alt=media&token=a73b5d6a-6412-4b92-a358-10a61405e446",
             category = Category.PREWORKOUT
         )


         val product7 = Product(
             productName = "EVLUTION NUTRITION ENGN SHRED PRE WORKOUT",
             price = 39.99,
             quantity = 88,
             description = "ENGN SHRED’s revolutionary Fat Burning Pre-Workout formula is engineered to be the most complete, fully dosed thermogenic fuel that burns fat and boosts metabolism while accelerating your performance, energy, power and focus. Plus, it’s formulated to avoid the crash of other pre-workouts in a clean formula with zero sugar, zero carbs and zero calories. Achieve more with ENGN SHRED to fuel your results and lean body goals!",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2Fengn-shred-30serv-sk_1121_grey_900x.webp?alt=media&token=f5b279ec-d913-4e76-a245-16ca5e23bafc",
             category = Category.PREWORKOUT
         )

         val product8 = Product(
             productName = "JYM SUPPLEMENT SCIENCE PRE JYM PRE WORKOUT POWDER",
             price = 44.99,
             quantity = 56,
             description = "Pre JYM is Bodybuilding.com’s best-selling pre-workout product since it launched in 2013. Pre JYM includes 13 ingredients that complement one another to prime the body for intense workouts, making it a complete pre-workout supplement.\n" +
                     "\n" +
                     "No other pre-workout supplement surpasses the great taste, texture, and nutrition of Pre JYM. Simply mix with 12-32 ounces of water and drink 30 minutes before your workout for the best results.",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FJYM5100008-Orange-Mango30-7-15_900x.webp?alt=media&token=3d1502be-d4e4-4f34-b348-c7c137fbacec",
             category = Category.PREWORKOUT
         )

         val product9 = Product(
             productName = "EVLUTION NUTRITION TRANS4ORM FAT BURNER",
             price = 22.49,
             quantity = 140,
             description = "TRANS4ORM® is a scientifically-developed multi-stage thermogenic energizer and weight loss support supplement that works with your body to transform fat and build lean muscle.\n" +
                     "When reaching your weight loss goals, it is important to give your body all the tools it needs to support a faster metabolism, control appetite, with the energy, focus, and mood you need to perform at your best.\n" +
                     "Uncompromising quality standards backed up by researched ingredients, transparent formulas, and GMP certified.",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FTRANS4ORM-30SERV-PL_grey_900x.webp?alt=media&token=15dea52e-93b0-4fb0-8477-bd5026246fba",
             category = Category.FATBURNER
         )


         val product10 = Product(
             productName = "RSP NUTRITION QUADRALEAN THERMO FAT BURNER",
             price = 26.22,
             quantity = 7,
             description = "QuadraLean Thermogenic was designed using the best and most effective weight management ingredients. Our comprehensive formula has been developed to target all critical areas of weight loss. Boost metabolism & target stubborn fat with efficacious doses of L-Carnitine & CLA.\n" +
                     "\n" +
                     "Support thermogenesis with our scientifically researched Thermogenesis Blend, featuring Cayenne Pepper Extract. Enjoy a boost of clean, sustained energy with natural caffeine from Green Tea and experience increased mental focus through our research-backed Neuro Blend with Alpha-GPC. The combination of all these benefits sets QuadraLean Thermogenic apart as a truly complete thermogenic weight management solution.",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FRSP4360084_grey_900x.webp?alt=media&token=682969a4-bb3b-43b1-9e8c-56a4485ee071",
             category = Category.FATBURNER
         )


         val product11 = Product(
             productName = "BODYBUILDING.COM SIGNATURE CLA WEIGHT LOSS SUPPLEMENT",
             price = 11.24,
             quantity = 90,
             description = "Signature CLA supports your metabolism and helps your body use fat as fuel—without the use of stimulants. CLA is an omega-6 fatty acid that is found in meat and dairy, but you have to eat a lot of both to reap the benefits. Signature CLA distills those omega-6 acids into an easy-to-take supplement.",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FBBCOM_NEW_Signature_CLA_V6_grey_900x.webp?alt=media&token=c40fd495-176e-44f9-9fd5-d9711c906887",
             category = Category.FATBURNER
         )


         val product12 = Product(
             productName = "EVLUTION NUTRITION LEANMODE WEIGHT-LOSS SUPPORT",
             price = 28.49,
             quantity = 96,
             description = "LEANMODE combines the 5 leading stimulant-free ingredients for weight loss into one full-spectrum formula. LEANMODE’s fully dosed ingredients support all the areas critical for effective weight loss including release of stored fat, fat burning, appetite support, and metabolism support. LEANMODE’s 100% transparent formula of 5 powerful ingredients includes Green Coffee, Green Tea, CLA, Acetyl L Carnitine and Garcinia Cambogia which offer unique modes of actions to help burn fat and control your appetite morning and night, and it’s made in a GMP certified facility to produce at the highest quality standards.",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FLEANMODE30SERVPS_grey_900x.webp?alt=media&token=be536013-f5de-47b2-9806-23b5693553be",
             category = Category.FATBURNER
         )

         val product13 = Product(
             productName = "JYM MASS JYM WEIGHT GAINER PROTEIN",
             price = 69.99,
             quantity = 21,
             description = "Mass JYM is the first mass-gainer to use a non-proprietary protein blend (Pro JYM) along with a non-proprietary low-glycemic carbohydrate source, and a non-proprietary healthy fat source. Unlike like most mass-gainers that include twice as many carbs as protein, Mass JYM contains a 1:1 ratio of anabolic protein to low-GI carbs for “clean” gains.\n" +
                     "\n" +
                     "Whether you use it as a meal replacement on the go or as a mass gainer, rest assured that you’re getting quality ingredients and quality calories that, when combined with training and a proper whole food diet, will go toward packing on lean muscle mass, not excess body fat.",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FJYM5210020_grey_900x.webp?alt=media&token=482fd3ca-770a-40e8-b006-04ddf3cb9ebc",
             category = Category.MASSGAINER
         )


         val product14 = Product(
             productName = "OPTIMUM NUTRITION SERIOUS MASS WEIGHT GAINER",
             price = 72.99,
             quantity = 5,
             description = "Serious Mass is the ultimate in weight gain formulas.",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FOPT157_grey_900x.webp?alt=media&token=1aaa17dd-c3ae-4af0-814f-43d385905f82",
             category = Category.MASSGAINER
         )


         val product15 = Product(
             productName = "SWOLVERINE CLEAN CARBS",
             price = 54.99,
             quantity = 35,
             description = "Athletes need more fuel than the average gym-goer, in order to perform at a higher level. That fuel is glycogen, which is derived from carbohydrates. Studies have shown for the past several decades, that glycogen replenishment is key to fuel endurance and post-workout recovery during prolonged periods of exercise performance. However, not all carbohydrates are created equal and come in a variety of different forms. Clean carbohydrate sources or complex carbohydrates from real food, provide the long-lasting sustained energy you need for prolonged exercise activity, such as running, cycling, swimming, and high-intensity training. Clean Carbs also help in optimizing body composition, weight loss, and better digestive health.",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FSWOL6070138_grey_900x.webp?alt=media&token=7dcf6408-44e2-44cc-b0b4-d52a065fe40c",
             category = Category.MASSGAINER
         )


         val product16 = Product(
             productName = "MUSCLETECH 100% MASS GAINER",
             price = 29.74,
             quantity = 278,
             description = "Are you a hard gainer? Whether you’ve struggled with adding size or you want to break through your plateaus, we’ve got your back. Our 100% Mass Gainer is packed with high-quality protein and mass-producing calories, plus muscle-fueling creatine, a proven anabolic driver for faster mass and strength gains. This superior formula with proven muscle building ingredients will help you get bigger and stronger – faster than you ever imagined.",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FMT6330093_grey_900x.webp?alt=media&token=2d187b02-84e2-4b04-aad4-8008251c7ece",
             category = Category.MASSGAINER
         )



         val product17 = Product(
             productName = "KAGED PRO-BIOTIC",
             price = 34.99,
             quantity = 56,
             description = "Pro-Biotic™ contains a unique, patented strain of L. plantarum called TWK10®. Naturally fermented from Taiwanese Kimchi, TWK10® takes everyday gut health to the next level. By targeting the gut-muscle axis, this clean-label, vegan-friendly probiotic strain has been clinically shown to improve training performance.\n" +
                     "By enhancing your body’s natural energy production through glycolysis, Pro-Biotic™ helps you generate more ATP (adenosine triphosphate) to fuel your muscles. More ATP during exercise means you can fire through more reps, rest less between sets, run farther, and perform at your peak for longer.",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FKAG6380430_grey_900x.webp?alt=media&token=c5c4096e-bd32-48e0-bd67-f83acca8085d",
             category = Category.PROBIOTIC
         )


         val product18 = Product(
             productName = "EVLUTION NUTRITION CLEANSEMODE APPLE CIDER VINEGAR",
             price = 14.49,
             quantity = 88,
             description = "Evlution Nutrition Apple Cider Vinegar is made from pure Non-GMO apples and is cold filtered to maintain potency and the broad spectrum of benefits. Our Apple Cider Vinegar capsules are formulated with Cayenne Pepper which contains the active ingredient capsaicin to help curb your appetite and support your metabolism. Plus, it’s formulated to the highest standard and produced in the USA under stringent quality control that exceeds certified GMP (good manufacturing practices) standards.",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FEVL5450050_grey_900x.webp?alt=media&token=5f4ec88b-d06a-4d2a-bece-7ef3a497f19f",
             category = Category.PROBIOTIC
         )


         val product19 = Product(
             productName = "IRWIN NATURALS ACTIVE-CLEANSE AND PROBIOTICS",
             price = 23.99,
             quantity = 1,
             description = "Probiotics bring balance to the intestinal tract and can help to replenish, rebuild and nourish the digestive system overall.",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FIN2440056_grey_900x.webp?alt=media&token=fe2088b1-3ab8-4b68-aa6f-0f9f662137b5",
             category = Category.PROBIOTIC
         )

         val product20 = Product(
             productName = "NOW DANDELION ROOT",
             price = 7.99,
             quantity = 18,
             description = "Dandelion (Taraxacum officinale) is a common flowering plant found throughout the temperate zones of the Northern Hemisphere. Dandelion root has a long history of use by Native American, European, and Asian herbalists. Dandelion root has many naturally occurring compounds including inulin, sesquiterpenes, and phenolic compounds such as flavonoids, minerals, and phytosterols. Natural color variation may occur in this product.",
             productPictureLink = "https://firebasestorage.googleapis.com/v0/b/nutshop-e5883.appspot.com/o/productImages%2FNOW200_grey_900x.webp?alt=media&token=da545a8d-e128-4d43-ac08-45f0c353b60d",
             category = Category.PROBIOTIC
         )


         Firebase.firestore.collection("products").document(product1.productId).set(product1)
         Firebase.firestore.collection("products").document(product2.productId).set(product2)
         Firebase.firestore.collection("products").document(product3.productId).set(product3)
         Firebase.firestore.collection("products").document(product4.productId).set(product4)
         Firebase.firestore.collection("products").document(product5.productId).set(product5)
         Firebase.firestore.collection("products").document(product6.productId).set(product6)
         Firebase.firestore.collection("products").document(product7.productId).set(product7)
         Firebase.firestore.collection("products").document(product8.productId).set(product8)
         Firebase.firestore.collection("products").document(product9.productId).set(product9)
         Firebase.firestore.collection("products").document(product10.productId).set(product10)
         Firebase.firestore.collection("products").document(product11.productId).set(product11)
         Firebase.firestore.collection("products").document(product12.productId).set(product12)
         Firebase.firestore.collection("products").document(product13.productId).set(product13)
         Firebase.firestore.collection("products").document(product14.productId).set(product14)
         Firebase.firestore.collection("products").document(product15.productId).set(product15)
         Firebase.firestore.collection("products").document(product16.productId).set(product16)
         Firebase.firestore.collection("products").document(product17.productId).set(product17)
         Firebase.firestore.collection("products").document(product18.productId).set(product18)
         Firebase.firestore.collection("products").document(product19.productId).set(product19)
         Firebase.firestore.collection("products").document(product20.productId).set(product20)

     }*/
}