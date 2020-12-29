package com.iblogstreet.mpchartdemo.bean;

import java.util.List;

/**
 * @author junwang
 * @date 2020/12/29 2:19 PM
 */
public class StockBean {

    /**
     * pagination : {"limit":100,"offset":0,"count":100,"total":252}
     * data : [{"open":133.99,"high":137.34,"low":133.51,"close":136.69,"volume":1.23124632E8,"adj_high":137.34,"adj_low":133.51,"adj_close":136.69,"adj_open":133.99,"adj_volume":1.23124632E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-28T00:00:00+0000"},{"open":131.32,"high":133.46,"low":131.1,"close":131.97,"volume":5.4930064E7,"adj_high":133.46,"adj_low":131.1,"adj_close":131.97,"adj_open":131.32,"adj_volume":5.4930064E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-24T00:00:00+0000"},{"open":132.16,"high":132.43,"low":130.78,"close":130.96,"volume":8.8223692E7,"adj_high":132.43,"adj_low":130.78,"adj_close":130.96,"adj_open":132.16,"adj_volume":8.8223692E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-23T00:00:00+0000"},{"open":131.61,"high":134.405,"low":129.65,"close":131.88,"volume":1.69351825E8,"adj_high":134.405,"adj_low":129.65,"adj_close":131.88,"adj_open":131.61,"adj_volume":1.69351825E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-22T00:00:00+0000"},{"open":125.02,"high":128.31,"low":123.449,"close":128.23,"volume":1.21251553E8,"adj_high":128.31,"adj_low":123.449,"adj_close":128.23,"adj_open":125.02,"adj_volume":1.21251553E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-21T00:00:00+0000"},{"open":128.96,"high":129.1,"low":126.12,"close":126.655,"volume":1.92541496E8,"adj_high":129.1,"adj_low":126.12,"adj_close":126.655,"adj_open":128.96,"adj_volume":1.92541496E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-18T00:00:00+0000"},{"open":128.9,"high":129.58,"low":128.045,"close":128.7,"volume":9.4359811E7,"adj_high":129.58,"adj_low":128.045,"adj_close":128.7,"adj_open":128.9,"adj_volume":9.4359811E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-17T00:00:00+0000"},{"open":127.41,"high":128.37,"low":126.56,"close":127.81,"volume":9.8208591E7,"adj_high":128.37,"adj_low":126.56,"adj_close":127.81,"adj_open":127.41,"adj_volume":9.8208591E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-16T00:00:00+0000"},{"open":124.34,"high":127.9,"low":124.13,"close":127.88,"volume":1.57572262E8,"adj_high":127.9,"adj_low":124.13,"adj_close":127.88,"adj_open":124.34,"adj_volume":1.57572262E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-15T00:00:00+0000"},{"open":122.6,"high":123.35,"low":121.54,"close":121.78,"volume":7.9075988E7,"adj_high":123.35,"adj_low":121.54,"adj_close":121.78,"adj_open":122.6,"adj_volume":7.9075988E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-14T00:00:00+0000"},{"open":122.43,"high":122.76,"low":120.55,"close":122.41,"volume":8.6939786E7,"adj_high":122.76,"adj_low":120.55,"adj_close":122.41,"adj_open":122.43,"adj_volume":8.6939786E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-11T00:00:00+0000"},{"open":120.5,"high":123.87,"low":120.15,"close":123.24,"volume":8.131217E7,"adj_high":123.87,"adj_low":120.15,"adj_close":123.24,"adj_open":120.5,"adj_volume":8.131217E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-10T00:00:00+0000"},{"open":124.53,"high":125.95,"low":121,"close":121.78,"volume":1.15089193E8,"adj_high":125.95,"adj_low":121,"adj_close":121.78,"adj_open":124.53,"adj_volume":1.15089193E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-09T00:00:00+0000"},{"open":124.37,"high":124.98,"low":123.09,"close":124.38,"volume":8.2225512E7,"adj_high":124.98,"adj_low":123.09,"adj_close":124.38,"adj_open":124.37,"adj_volume":8.2225512E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-08T00:00:00+0000"},{"open":122.31,"high":124.57,"low":122.25,"close":123.75,"volume":8.671199E7,"adj_high":124.57,"adj_low":122.25,"adj_close":123.75,"adj_open":122.31,"adj_volume":8.671199E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-07T00:00:00+0000"},{"open":122.6,"high":122.8608,"low":121.52,"close":122.25,"volume":7.8260421E7,"adj_high":122.8608,"adj_low":121.52,"adj_close":122.25,"adj_open":122.6,"adj_volume":7.8260421E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-04T00:00:00+0000"},{"open":123.52,"high":123.78,"low":122.21,"close":122.94,"volume":7.896763E7,"adj_high":123.78,"adj_low":122.21,"adj_close":122.94,"adj_open":123.52,"adj_volume":7.896763E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-03T00:00:00+0000"},{"open":122.02,"high":123.37,"low":120.89,"close":123.08,"volume":8.9004195E7,"adj_high":123.37,"adj_low":120.89,"adj_close":123.08,"adj_open":122.02,"adj_volume":8.9004195E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-02T00:00:00+0000"},{"open":121.01,"high":123.4693,"low":120.01,"close":122.72,"volume":1.25920963E8,"adj_high":123.4693,"adj_low":120.01,"adj_close":122.72,"adj_open":121.01,"adj_volume":1.25920963E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-12-01T00:00:00+0000"},{"open":116.97,"high":120.97,"low":116.81,"close":119.05,"volume":1.69410176E8,"adj_high":120.97,"adj_low":116.81,"adj_close":119.05,"adj_open":116.97,"adj_volume":1.69410176E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-30T00:00:00+0000"},{"open":116.57,"high":117.49,"low":116.22,"close":116.59,"volume":4.6691331E7,"adj_high":117.49,"adj_low":116.22,"adj_close":116.59,"adj_open":116.57,"adj_volume":4.6691331E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-27T00:00:00+0000"},{"open":115.55,"high":116.75,"low":115.17,"close":116.03,"volume":7.6499234E7,"adj_high":116.75,"adj_low":115.17,"adj_close":116.03,"adj_open":115.55,"adj_volume":7.6499234E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-25T00:00:00+0000"},{"open":113.91,"high":115.85,"low":112.59,"close":115.17,"volume":1.13226248E8,"adj_high":115.85,"adj_low":112.59,"adj_close":115.17,"adj_open":113.91,"adj_volume":1.13226248E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-24T00:00:00+0000"},{"open":117.18,"high":117.6202,"low":113.75,"close":113.85,"volume":1.27959318E8,"adj_high":117.6202,"adj_low":113.75,"adj_close":113.85,"adj_open":117.18,"adj_volume":1.27959318E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-23T00:00:00+0000"},{"open":118.64,"high":118.77,"low":117.29,"close":117.34,"volume":7.3604287E7,"adj_high":118.77,"adj_low":117.29,"adj_close":117.34,"adj_open":118.64,"adj_volume":7.3604287E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-20T00:00:00+0000"},{"open":117.59,"high":119.06,"low":116.81,"close":118.64,"volume":7.4112972E7,"adj_high":119.06,"adj_low":116.81,"adj_close":118.64,"adj_open":117.59,"adj_volume":7.4112972E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-19T00:00:00+0000"},{"open":118.61,"high":119.82,"low":118,"close":118.03,"volume":7.6322111E7,"adj_high":119.82,"adj_low":118,"adj_close":118.03,"adj_open":118.61,"adj_volume":7.6322111E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-18T00:00:00+0000"},{"open":119.55,"high":120.6741,"low":118.96,"close":119.39,"volume":7.4270973E7,"adj_high":120.6741,"adj_low":118.96,"adj_close":119.39,"adj_open":119.55,"adj_volume":7.4270973E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-17T00:00:00+0000"},{"open":118.92,"high":120.99,"low":118.146,"close":120.3,"volume":9.1183018E7,"adj_high":120.99,"adj_low":118.146,"adj_close":120.3,"adj_open":118.92,"adj_volume":9.1183018E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-16T00:00:00+0000"},{"open":119.44,"high":119.6717,"low":117.87,"close":119.26,"volume":8.1688586E7,"adj_high":119.6717,"adj_low":117.87,"adj_close":119.26,"adj_open":119.44,"adj_volume":8.1688586E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-13T00:00:00+0000"},{"open":119.62,"high":120.53,"low":118.57,"close":119.21,"volume":1.03350674E8,"adj_high":120.53,"adj_low":118.57,"adj_close":119.21,"adj_open":119.62,"adj_volume":1.03350674E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-12T00:00:00+0000"},{"open":117.19,"high":119.63,"low":116.44,"close":119.49,"volume":1.12294954E8,"adj_high":119.63,"adj_low":116.44,"adj_close":119.49,"adj_open":117.19,"adj_volume":1.12294954E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-11T00:00:00+0000"},{"open":115.55,"high":117.59,"low":114.13,"close":115.97,"volume":1.3802339E8,"adj_high":117.59,"adj_low":114.13,"adj_close":115.97,"adj_open":115.55,"adj_volume":1.3802339E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-10T00:00:00+0000"},{"open":120.5,"high":121.99,"low":116.05,"close":116.32,"volume":1.54515315E8,"adj_high":121.99,"adj_low":116.05,"adj_close":116.32,"adj_open":120.5,"adj_volume":1.54515315E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-09T00:00:00+0000"},{"open":118.32,"high":119.2,"low":116.13,"close":118.69,"volume":1.14457922E8,"adj_high":119.2,"adj_low":116.13,"adj_close":118.69,"adj_open":118.32,"adj_volume":1.14457922E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-06T00:00:00+0000"},{"open":117.95,"high":119.62,"low":116.8686,"close":119.03,"volume":1.26387074E8,"adj_high":119.4137499474,"adj_low":116.66709394,"adj_close":118.8247672316,"adj_open":117.7466293789,"adj_volume":1.26387074E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-05T00:00:00+0000"},{"open":114.14,"high":115.59,"low":112.35,"close":114.95,"volume":1.38235482E8,"adj_high":115.3906985155,"adj_low":112.1562849573,"adj_close":114.7518020102,"adj_open":113.9431986206,"adj_volume":1.38235482E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-04T00:00:00+0000"},{"open":109.66,"high":111.49,"low":108.73,"close":110.44,"volume":1.07624448E8,"adj_high":111.2977677783,"adj_low":108.5425265991,"adj_close":110.2495781993,"adj_open":109.4709230834,"adj_volume":1.07624448E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-03T00:00:00+0000"},{"open":109.11,"high":110.68,"low":107.32,"close":108.77,"volume":1.22866899E8,"adj_high":110.4891643887,"adj_low":107.1349577358,"adj_close":108.5824576307,"adj_open":108.9218713991,"adj_volume":1.22866899E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-11-02T00:00:00+0000"},{"open":111.06,"high":111.99,"low":107.72,"close":108.86,"volume":1.90573476E8,"adj_high":111.7969056731,"adj_low":107.5342680516,"adj_close":108.6723024517,"adj_open":110.8685091888,"adj_volume":1.90573476E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-30T00:00:00+0000"},{"open":112.37,"high":116.93,"low":112.2,"close":115.32,"volume":1.46129173E8,"adj_high":116.7283880735,"adj_low":112.0065435889,"adj_close":115.1211640523,"adj_open":112.1762504731,"adj_volume":1.46129173E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-29T00:00:00+0000"},{"open":115.05,"high":115.43,"low":111.1,"close":111.2,"volume":1.43937823E8,"adj_high":115.2309743892,"adj_low":110.9084402204,"adj_close":111.0082677993,"adj_open":114.8516295891,"adj_volume":1.43937823E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-28T00:00:00+0000"},{"open":115.49,"high":117.28,"low":114.5399,"close":116.6,"volume":9.2276772E7,"adj_high":117.0777845999,"adj_low":114.3424091089,"adj_close":116.398957063,"adj_open":115.2908709365,"adj_volume":9.2276772E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-27T00:00:00+0000"},{"open":114.01,"high":116.55,"low":112.88,"close":115.05,"volume":1.11850657E8,"adj_high":116.3490432735,"adj_low":112.6853711258,"adj_close":114.8516295891,"adj_open":113.813422768,"adj_volume":1.11850657E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-26T00:00:00+0000"},{"open":116.39,"high":116.55,"low":114.28,"close":115.04,"volume":8.2572645E7,"adj_high":116.3490432735,"adj_low":114.0829572312,"adj_close":114.8416468312,"adj_open":116.1893191471,"adj_volume":8.2572645E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-23T00:00:00+0000"},{"open":117.45,"high":118.04,"low":114.59,"close":115.75,"volume":1.01987954E8,"adj_high":117.8364741999,"adj_low":114.3924227259,"adj_close":115.5504226418,"adj_open":117.2474914841,"adj_volume":1.01987954E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-22T00:00:00+0000"},{"open":116.67,"high":118.705,"low":116.45,"close":116.87,"volume":8.994598E7,"adj_high":118.5003276,"adj_low":116.2492156945,"adj_close":116.6684915261,"adj_open":116.4688363682,"adj_volume":8.994598E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-21T00:00:00+0000"},{"open":116.2,"high":118.98,"low":115.63,"close":117.51,"volume":1.24423728E8,"adj_high":118.7748534421,"adj_low":115.4306295471,"adj_close":117.3073880315,"adj_open":115.9996467471,"adj_volume":1.24423728E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-20T00:00:00+0000"},{"open":119.96,"high":120.419,"low":115.66,"close":115.98,"volume":1.20639337E8,"adj_high":120.2113723033,"adj_low":115.4605778208,"adj_close":115.7800260734,"adj_open":119.7531637159,"adj_volume":1.20639337E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-19T00:00:00+0000"},{"open":121.28,"high":121.548,"low":118.81,"close":119.02,"volume":1.15393808E8,"adj_high":121.3384256697,"adj_low":118.6051465579,"adj_close":118.8147844737,"adj_open":121.0708877581,"adj_volume":1.15393808E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-16T00:00:00+0000"},{"open":118.72,"high":121.2,"low":118.15,"close":120.71,"volume":1.12559219E8,"adj_high":120.9910256949,"adj_low":117.9462845368,"adj_close":120.5018705581,"adj_open":118.5153017368,"adj_volume":1.12559219E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-15T00:00:00+0000"},{"open":121,"high":123.03,"low":119.62,"close":121.19,"volume":1.51062308E8,"adj_high":122.8178703898,"adj_low":119.4137499474,"adj_close":120.981042937,"adj_open":120.791370537,"adj_volume":1.51062308E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-14T00:00:00+0000"},{"open":125.27,"high":125.39,"low":119.65,"close":121.1,"volume":2.62330451E8,"adj_high":125.1738012532,"adj_low":119.4436982211,"adj_close":120.891198116,"adj_open":125.0540081585,"adj_volume":2.62330451E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-13T00:00:00+0000"},{"open":120.06,"high":125.18,"low":119.2845,"close":124.4,"volume":2.40226769E8,"adj_high":124.9641633374,"adj_low":119.07882842,"adj_close":124.1855082215,"adj_open":119.8529912948,"adj_volume":2.40226769E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-12T00:00:00+0000"},{"open":115.28,"high":117,"low":114.92,"close":116.97,"volume":1.00506865E8,"adj_high":116.7982673788,"adj_low":114.7218537365,"adj_close":116.7683191051,"adj_open":115.0812330207,"adj_volume":1.00506865E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-09T00:00:00+0000"},{"open":116.25,"high":116.4,"low":114.5901,"close":114.97,"volume":8.3477153E7,"adj_high":116.199301905,"adj_low":114.3925225535,"adj_close":114.771767526,"adj_open":116.0495605366,"adj_volume":8.3477153E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-08T00:00:00+0000"},{"open":114.62,"high":115.55,"low":114.13,"close":115.08,"volume":9.6848985E7,"adj_high":115.3507674839,"adj_low":113.9332158627,"adj_close":114.8815778628,"adj_open":114.4223709996,"adj_volume":9.6848985E7,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-07T00:00:00+0000"},{"open":115.7,"high":116.12,"low":112.25,"close":113.16,"volume":1.61498212E8,"adj_high":115.919784684,"adj_low":112.0564573784,"adj_close":112.9648883469,"adj_open":115.5005088523,"adj_volume":1.61498212E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-06T00:00:00+0000"},{"open":113.91,"high":116.65,"low":113.55,"close":116.5,"volume":1.06243839E8,"adj_high":116.4488708524,"adj_low":113.3542159048,"adj_close":116.299129484,"adj_open":113.713595189,"adj_volume":1.06243839E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-05T00:00:00+0000"},{"open":112.89,"high":115.37,"low":112.22,"close":113.02,"volume":1.44711986E8,"adj_high":115.1710778418,"adj_low":112.0265091047,"adj_close":112.8251297363,"adj_open":112.6953538837,"adj_volume":1.44711986E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-02T00:00:00+0000"},{"open":117.64,"high":117.72,"low":115.83,"close":116.79,"volume":1.1612044E8,"adj_high":117.5170259473,"adj_low":115.630284705,"adj_close":116.588629463,"adj_open":117.4371638841,"adj_volume":1.1612044E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-10-01T00:00:00+0000"},{"open":113.79,"high":117.26,"low":113.62,"close":115.81,"volume":1.42675184E8,"adj_high":117.0578190841,"adj_low":113.4240952101,"adj_close":115.6103191892,"adj_open":113.5938020943,"adj_volume":1.42675184E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-30T00:00:00+0000"},{"open":114.55,"high":115.31,"low":113.57,"close":114.09,"volume":1.00060526E8,"adj_high":115.1111812944,"adj_low":113.3741814206,"adj_close":113.8932848312,"adj_open":114.3524916944,"adj_volume":1.00060526E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-29T00:00:00+0000"},{"open":115.01,"high":115.32,"low":112.78,"close":114.96,"volume":1.37672403E8,"adj_high":115.1211640523,"adj_low":112.5855435468,"adj_close":114.7617847681,"adj_open":114.8116985576,"adj_volume":1.37672403E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-28T00:00:00+0000"},{"open":108.43,"high":112.44,"low":107.67,"close":112.28,"volume":1.49981441E8,"adj_high":112.2461297784,"adj_low":107.4843542622,"adj_close":112.086405652,"adj_open":108.2430438622,"adj_volume":1.49981441E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-25T00:00:00+0000"},{"open":105.17,"high":110.25,"low":105,"close":108.22,"volume":1.67743349E8,"adj_high":110.0599057992,"adj_low":104.818957904,"adj_close":108.0334059464,"adj_open":104.9886647883,"adj_volume":1.67743349E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-24T00:00:00+0000"},{"open":111.62,"high":112.11,"low":106.77,"close":107.12,"volume":1.50718671E8,"adj_high":111.9166987678,"adj_low":106.5859060516,"adj_close":106.9353025779,"adj_open":111.4275436309,"adj_volume":1.50718671E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-23T00:00:00+0000"},{"open":112.68,"high":112.86,"low":109.16,"close":111.81,"volume":1.83055373E8,"adj_high":112.66540561,"adj_low":108.9717851886,"adj_close":111.617216031,"adj_open":112.4857159679,"adj_volume":1.83055373E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-22T00:00:00+0000"},{"open":104.54,"high":110.19,"low":103.1,"close":110.08,"volume":1.95713815E8,"adj_high":110.0000092519,"adj_low":102.9222339039,"adj_close":109.890198915,"adj_open":104.3597510408,"adj_volume":1.95713815E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-21T00:00:00+0000"},{"open":110.4,"high":110.88,"low":106.09,"close":106.84,"volume":2.87104882E8,"adj_high":110.6888195467,"adj_low":105.9070785147,"adj_close":106.6557853568,"adj_open":110.2096471677,"adj_volume":2.87104882E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-18T00:00:00+0000"},{"open":109.72,"high":112.2,"low":108.71,"close":110.34,"volume":1.78010968E8,"adj_high":112.0065435889,"adj_low":108.5225610833,"adj_close":110.1497506203,"adj_open":109.5308196308,"adj_volume":1.78010968E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-17T00:00:00+0000"},{"open":115.23,"high":116,"low":112.04,"close":112.13,"volume":1.55026675E8,"adj_high":115.7999915892,"adj_low":111.8468194626,"adj_close":111.9366642836,"adj_open":115.0313192313,"adj_volume":1.55026675E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-16T00:00:00+0000"},{"open":118.33,"high":118.829,"low":113.61,"close":115.54,"volume":1.84642039E8,"adj_high":118.6241137979,"adj_low":113.4141124522,"adj_close":115.340784726,"adj_open":118.1259741789,"adj_volume":1.84642039E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-15T00:00:00+0000"},{"open":114.72,"high":115.93,"low":112.8,"close":115.355,"volume":1.40150087E8,"adj_high":115.7301122839,"adj_low":112.6055090626,"adj_close":115.1561037049,"adj_open":114.5221985786,"adj_volume":1.40150087E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-14T00:00:00+0000"},{"open":114.57,"high":115.23,"low":110,"close":112,"volume":1.80860325E8,"adj_high":115.0313192313,"adj_low":109.8103368518,"adj_close":111.806888431,"adj_open":114.3724572101,"adj_volume":1.80860325E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-11T00:00:00+0000"},{"open":120.36,"high":120.5,"low":112.5,"close":113.49,"volume":1.82274391E8,"adj_high":120.2922326422,"adj_low":112.3060263257,"adj_close":113.2943193574,"adj_open":120.1524740317,"adj_volume":1.82274391E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-10T00:00:00+0000"},{"open":117.26,"high":119.14,"low":115.26,"close":117.32,"volume":1.76940455E8,"adj_high":118.9345775684,"adj_low":115.0612675049,"adj_close":117.1177156314,"adj_open":117.0578190841,"adj_volume":1.76940455E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-09T00:00:00+0000"},{"open":113.95,"high":118.99,"low":112.68,"close":112.82,"volume":2.31366563E8,"adj_high":118.7848362,"adj_low":112.4857159679,"adj_close":112.6254745784,"adj_open":113.7535262206,"adj_volume":2.31366563E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-08T00:00:00+0000"},{"open":120.07,"high":123.7,"low":110.89,"close":120.96,"volume":3.32607163E8,"adj_high":123.4867151688,"adj_low":110.6988023046,"adj_close":120.7514395054,"adj_open":119.8629740527,"adj_volume":3.32607163E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-04T00:00:00+0000"},{"open":126.91,"high":128.84,"low":120.5,"close":120.88,"volume":2.5759964E8,"adj_high":128.6178527272,"adj_low":120.2922326422,"adj_close":120.6715774423,"adj_open":126.6911804533,"adj_volume":2.5759964E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-03T00:00:00+0000"},{"open":137.59,"high":137.98,"low":127,"close":131.4,"volume":2.00118991E8,"adj_high":137.7420934438,"adj_low":126.7810252744,"adj_close":131.1734387485,"adj_open":137.3527658859,"adj_volume":2.00118991E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-02T00:00:00+0000"},{"open":132.76,"high":134.8,"low":130.53,"close":134.18,"volume":1.52470142E8,"adj_high":134.567576433,"adj_low":130.3049388116,"adj_close":133.9486454435,"adj_open":132.5310938223,"adj_volume":1.52470142E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-09-01T00:00:00+0000"},{"open":127.58,"high":131,"low":126,"close":129.04,"volume":2.23505733E8,"adj_high":130.7741284327,"adj_low":125.7827494848,"adj_close":128.8175078851,"adj_open":127.3600252323,"adj_volume":2.23505733E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-31T00:00:00+0000"},{"open":504.05,"high":505.77,"low":498.31,"close":499.23,"volume":4.6907479E7,"adj_high":126.2244865217,"adj_low":124.3627021742,"adj_close":124.5923056058,"adj_open":125.7952279322,"adj_volume":1.87629916E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-28T00:00:00+0000"},{"open":508.57,"high":509.94,"low":495.33,"close":500.04,"volume":3.8888096E7,"adj_high":127.2651890323,"adj_low":123.618986711,"adj_close":124.7944564532,"adj_open":126.9232795744,"adj_volume":1.55552384E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-27T00:00:00+0000"},{"open":504.7165,"high":507.97,"low":500.33,"close":506.09,"volume":4.0755567E7,"adj_high":126.773538206,"adj_low":124.8668314479,"adj_close":126.3043485849,"adj_open":125.9615656356,"adj_volume":1.63022268E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-26T00:00:00+0000"},{"open":498.79,"high":500.7172,"low":492.21,"close":499.3,"volume":5.2873947E7,"adj_high":124.9634645443,"adj_low":122.8403315951,"adj_close":124.6097754321,"adj_open":124.4824952689,"adj_volume":2.11495788E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-25T00:00:00+0000"},{"open":514.79,"high":515.14,"low":495.745,"close":503.43,"volume":8.6484442E7,"adj_high":128.5629475588,"adj_low":123.7225578241,"adj_close":125.6404951848,"adj_open":128.4755984272,"adj_volume":3.45937768E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-24T00:00:00+0000"},{"open":477.05,"high":499.472,"low":477,"close":497.48,"volume":8.451366E7,"adj_high":124.6527012911,"adj_low":119.0443879053,"adj_close":124.1555599479,"adj_open":119.0568663527,"adj_volume":3.3805464E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-21T00:00:00+0000"},{"open":463,"high":473.568,"low":462.9335,"close":473.1,"volume":3.1726797E7,"adj_high":118.1878672779,"adj_low":115.5338263068,"adj_close":118.0710690105,"adj_open":115.5504226418,"adj_volume":1.26907188E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-20T00:00:00+0000"},{"open":463.933,"high":468.65,"low":462.44,"close":462.83,"volume":3.6384502E7,"adj_high":116.9604871946,"adj_low":115.4106640313,"adj_close":115.5079959208,"adj_open":115.7832704697,"adj_volume":1.45538008E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-19T00:00:00+0000"},{"open":457.41,"high":464,"low":456.03,"close":462.25,"volume":2.6408385E7,"adj_high":115.7999915892,"adj_low":113.8109270785,"adj_close":115.3632459313,"adj_open":114.1553322259,"adj_volume":1.0563354E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-18T00:00:00+0000"},{"open":464.25,"high":464.35,"low":455.8501,"close":458.43,"volume":2.9431414E7,"adj_high":115.8873407208,"adj_low":113.7660296249,"adj_close":114.4098925523,"adj_open":115.8623838261,"adj_volume":1.17725656E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-17T00:00:00+0000"},{"open":459.315,"high":460,"low":452.18,"close":459.63,"volume":4.1391302E7,"adj_high":114.8017157997,"adj_low":112.8500866311,"adj_close":114.7093752891,"adj_open":114.6307610707,"adj_volume":1.65565208E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-14T00:00:00+0000"},{"open":457.72,"high":464.17,"low":455.71,"close":460.04,"volume":5.2520516E7,"adj_high":115.8424183103,"adj_low":113.7310650153,"adj_close":114.8116985576,"adj_open":114.2326985996,"adj_volume":2.10082064E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-13T00:00:00+0000"},{"open":441.99,"high":453.1,"low":441.19,"close":452.04,"volume":4.1486205E7,"adj_high":113.0796900627,"adj_low":110.1073238992,"adj_close":112.8151469784,"adj_open":110.3069790572,"adj_volume":1.6594482E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-12T00:00:00+0000"},{"open":447.875,"high":449.93,"low":436.4267,"close":437.5,"volume":4.6975594E7,"adj_high":112.2885564994,"adj_low":108.9185521321,"adj_close":109.1864144834,"adj_open":111.7756923125,"adj_volume":1.87902376E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-11T00:00:00+0000"},{"open":450.4,"high":455.1,"low":440,"close":450.91,"volume":5.3100856E7,"adj_high":113.5788279574,"adj_low":109.8103368518,"adj_close":112.5331340679,"adj_open":112.4058539047,"adj_volume":2.12403424E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-10T00:00:00+0000"},{"open":452.82,"high":454.7,"low":441.17,"close":444.45,"volume":4.9511403E7,"adj_high":113.4790003785,"adj_low":110.1023325203,"adj_close":110.9209186677,"adj_open":113.0098107574,"adj_volume":1.98045612E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-07T00:00:00+0000"},{"open":441.62,"high":457.65,"low":439.19,"close":455.61,"volume":5.0607225E7,"adj_high":114.0048923761,"adj_low":109.4063338417,"adj_close":113.4967093094,"adj_open":110.0116695534,"adj_volume":2.024289E8,"symbol":"AAPL","exchange":"XNAS","date":"2020-08-06T00:00:00+0000"}]
     */

    public PaginationBean pagination;
    public List<DataBean> data;

    public static class PaginationBean {
        /**
         * limit : 100
         * offset : 0
         * count : 100
         * total : 252
         */

        public int limit;
        public int offset;
        public int count;
        public int total;
    }

    public static class DataBean {
        /**
         * open : 133.99
         * high : 137.34
         * low : 133.51
         * close : 136.69
         * volume : 1.23124632E8
         * adj_high : 137.34
         * adj_low : 133.51
         * adj_close : 136.69
         * adj_open : 133.99
         * adj_volume : 1.23124632E8
         * symbol : AAPL
         * exchange : XNAS
         * date : 2020-12-28T00:00:00+0000
         */

        public float open;
        public float high;
        public float low;
        public float close;
        public float volume;
        public double adj_high;
        public double adj_low;
        public double adj_close;
        public double adj_open;
        public double adj_volume;
        public String symbol;
        public String exchange;
        public String date;

        public String getDateDesc() {
            return "";
        }
    }
}
