var jsonTable = {
  "tailleX": 3000,
  "tailleY": 2000,
  "couleur0": "Bleu",
  "couleur3000": "Vert",
  "marge": 190,
  "zonesInterdites": [
    {
      "id": "cherry_north",
      "forme": "polygone",
      "desc": "Cerise Nord",
      "active": true,
      "points": [
        {
          "x": 0,
          "y": 985
        },
        {
          "x": 0,
          "y": 1015
        },
        {
          "x": 300,
          "y": 1015
        },
        {
          "x": 300,
          "y": 985
        }
      ]
    },
    {
      "id": "cherry_south",
      "forme": "polygone",
      "desc": "Cerise Sud",
      "active": true,
      "points": [
        {
          "x": 3000,
          "y": 985
        },
        {
          "x": 3000,
          "y": 1015
        },
        {
          "x": 2700,
          "y": 1015
        },
        {
          "x": 2700,
          "y": 985
        }
      ]
    },
    {
      "id": "cherry_west",
      "forme": "polygone",
      "desc": "Cerise Ouest",
      "active": true,
      "points": [
        {
          "x": 1350,
          "y": 0
        },
        {
          "x": 1650,
          "y": 0
        },
        {
          "x": 1650,
          "y": 30
        },
        {
          "x": 1350,
          "y": 30
        }
      ]
    },
    {
      "id": "cherry_east",
      "forme": "polygone",
      "desc": "Cerise Est",
      "active": true,
      "points": [
        {
          "x": 1350,
          "y": 2000
        },
        {
          "x": 1650,
          "y": 2000
        },
        {
          "x": 1650,
          "y": 1970
        },
        {
          "x": 1350,
          "y": 1970
        }
      ]
    }
  ],
  "elementsJeu" : [
    {
      "id": "start3000_3",
      "forme": "polygone",
      "desc": "Depart Vert 3",
      "active": false,
      "points": [
        {
          "x": 930,
          "y": 0
        },
        {
          "x": 1320,
          "y": 0
        },
        {
          "x": 1320,
          "y": 420
        },
        {
          "x": 930,
          "y": 420
        }
      ]
    },
    {
      "id": "start0_3",
      "forme": "polygone",
      "desc": "Depart Bleu 3",
      "active": false,
      "points": [
        {
          "x": 930,
          "y": 2000
        },
        {
          "x": 1320,
          "y": 2000
        },
        {
          "x": 1320,
          "y": 1580
        },
        {
          "x": 930,
          "y": 1580
        }
      ]
    },

    {
      "id": "west_cake_purple_1",
      "forme" : "cercle",
      "desc": "Gateau violet ouest - 1",
      "active": true,
      "centre" :
      {
        "x" : 575,
        "y" : 225
      },
      "rayon": 60
    },
    {
      "id": "west_cake_yellow_1",
      "forme" : "cercle",
      "desc": "Gateau jaune ouest - 1",
      "active": true,
      "centre" :
      {
        "x" : 775,
        "y" : 225
      },
      "rayon": 60
    },
    {
      "id": "west_cake_brown_1",
      "forme" : "cercle",
      "desc": "Gateau brun ouest - 1",
      "active": true,
      "centre" :
      {
        "x" : 1125,
        "y" : 725
      },
      "rayon": 60
    },
    {
      "id": "west_cake_purple_2",
      "forme" : "cercle",
      "desc": "Gateau violet ouest - 2",
      "active": true,
      "centre" :
      {
        "x" : 2425,
        "y" : 225
      },
      "rayon": 60
    },
    {
      "id": "west_cake_yellow_2",
      "forme" : "cercle",
      "desc": "Gateau jaune ouest - 2",
      "active": true,
      "centre" :
      {
        "x" : 2225,
        "y" : 225
      },
      "rayon": 60
    },
    {
      "id": "west_cake_brown_2",
      "forme" : "cercle",
      "desc": "Gateau brun ouest - 2",
      "active": true,
      "centre" :
      {
        "x" : 1875,
        "y" : 725
      },
      "rayon": 60
    },

    {
      "id": "east_cake_purple_1",
      "forme" : "cercle",
      "desc": "Gateau violet est - 1",
      "active": true,
      "centre" :
      {
        "x" : 575,
        "y" : 1775
      },
      "rayon": 60
    },
    {
      "id": "east_cake_yellow_1",
      "forme" : "cercle",
      "desc": "Gateau jaune est - 1",
      "active": true,
      "centre" :
      {
        "x" : 775,
        "y" : 1775
      },
      "rayon": 60
    },
    {
      "id": "east_cake_brown_1",
      "forme" : "cercle",
      "desc": "Gateau brun est - 1",
      "active": true,
      "centre" :
      {
        "x" : 1125,
        "y" : 1275
      },
      "rayon": 60
    },
    {
      "id": "east_cake_purple_2",
      "forme" : "cercle",
      "desc": "Gateau violet est - 2",
      "active": true,
      "centre" :
      {
        "x" : 2425,
        "y" : 1775
      },
      "rayon": 60
    },
    {
      "id": "east_cake_yellow_2",
      "forme" : "cercle",
      "desc": "Gateau jaune est - 2",
      "active": true,
      "centre" :
      {
        "x" : 2225,
        "y" : 1775
      },
      "rayon": 60
    },
    {
      "id": "east_cake_brown_2",
      "forme" : "cercle",
      "desc": "Gateau brun est - 2",
      "active": true,
      "centre" :
      {
        "x" : 1875,
        "y" : 1275
      },
      "rayon": 60
    }
  ],
  "detectionIgnoreZone": [
  ]
};var strategyBig0 = [{ "task":"Position de départ","command":"start","position":{"x": 2720,"y": 1775,"theta": 3.141592653589793}},{ "task":"Step de départ bizarre","command":"go#1","position":{"x": 2719,"y": 1775,"theta": 3.141592653589793}},{ "task":"Init pince","command":"action#2","position":{"x": 2719,"y": 1775,"theta": 3.141592653589793}},{ "task":"Init ascenceur","command":"action#10","position":{"x": 2719,"y": 1775,"theta": 3.141592653589793}},{ "task":"Récupération violet 2","command":"goto#2630;1775","position":{"x": 2630,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise violet 2","command":"action#13","position":{"x": 2630,"y": 1775,"theta": 3.141592653589793}},{ "task":"Libération violet 2","command":"delete-zone#east_cake_purple_2","position":{"x": 2630,"y": 1775,"theta": 3.141592653589793}},{ "task":"Récupération jaune 2","command":"goto#2430;1775","position":{"x": 2430,"y": 1775,"theta": 3.141592653589793}},{ "task":"Récupération jaune 2","command":"face#0;1775","position":{"x": 2430,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#2","position":{"x": 2430,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"goto-back#2450;1775","position":{"x": 2450,"y": 1775,"theta": -3.141592653589793}},{ "task":"Prise jaune 2","command":"action#8","position":{"x": 2450,"y": 1775,"theta": -3.141592653589793}},{ "task":"Prise jaune 2","command":"action#9","position":{"x": 2450,"y": 1775,"theta": -3.141592653589793}},{ "task":"Prise jaune 2","command":"goto#2420;1775","position":{"x": 2420,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#0","position":{"x": 2420,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#7","position":{"x": 2420,"y": 1775,"theta": 3.141592653589793}},{ "task":"Libération jaune 2","command":"delete-zone#east_cake_yellow_2","position":{"x": 2420,"y": 1775,"theta": 3.141592653589793}},{ "task":"Go assiette 3","command":"goto-astar#2420;1770","position":{"x": 2420,"y": 1770,"theta": -1.5707963267948966}},{ "task":"Go assiette 3","command":"goto-astar#2200;1550","position":{"x": 2200,"y": 1550,"theta": 3.9269908169872414}},{ "task":"Go assiette 3","command":"goto-astar#1100;1550","position":{"x": 1100,"y": 1550,"theta": 3.141592653589793}},{ "task":"Alignement assiette 3","command":"face#1100;2000","position":{"x": 1100,"y": 1550,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Position largage 1","command":"goto#1100;1750","position":{"x": 1100,"y": 1750,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Position largage 1","command":"face#1100;2000","position":{"x": 1100,"y": 1750,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Depilement 1","command":"action#15","position":{"x": 1100,"y": 1750,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Position largage 2","command":"goto-back#1100;1650","position":{"x": 1100,"y": 1650,"theta": -4.71238898038469}},{ "task":"Assiette 3 - Position largage 2","command":"face#1100;2000","position":{"x": 1100,"y": 1650,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Depilement 2","command":"action#15","position":{"x": 1100,"y": 1650,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Position largage 3","command":"goto-back#1100;1550","position":{"x": 1100,"y": 1550,"theta": -4.71238898038469}},{ "task":"Assiette 3 - Position largage 3","command":"face#1100;2000","position":{"x": 1100,"y": 1550,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Depilement 3","command":"action#15","position":{"x": 1100,"y": 1550,"theta": 1.5707963267948966}},{ "task":"Libération assiette 3","command":"goto-back#1100;1350","position":{"x": 1100,"y": 1350,"theta": -4.71238898038469}},{ "task":"Blocage assiette 3","command":"add-zone#start0_3","position":{"x": 1100,"y": 1350,"theta": -4.71238898038469}},{ "task":"Libération brun 1","command":"delete-zone#east_cake_brown_1","position":{"x": 1100,"y": 1350,"theta": -4.71238898038469}},{ "task":"Go assiette 4","command":"goto-astar#1100;1350","position":{"x": 1100,"y": 1350,"theta": -4.71238898038469}},{ "task":"Go assiette 4","command":"goto-astar#1610;840","position":{"x": 1610,"y": 840,"theta": -0.7853981633974483}},{ "task":"Go assiette 4","command":"goto-astar#1610;480","position":{"x": 1610,"y": 480,"theta": -1.5707963267948966}},{ "task":"Go assiette 4","command":"goto-astar#1640;450","position":{"x": 1640,"y": 450,"theta": -0.7853981633974483}},{ "task":"Go assiette 4","command":"goto-astar#1750;450","position":{"x": 1750,"y": 450,"theta": 0.0}},{ "task":"Alignement assiette 4","command":"face#1750;0","position":{"x": 1750,"y": 450,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Position largage 1","command":"goto#1750;250","position":{"x": 1750,"y": 250,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Position largage 1","command":"face#1750;0","position":{"x": 1750,"y": 250,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Depilement 1","command":"action#15","position":{"x": 1750,"y": 250,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Position largage 2","command":"goto-back#1750;350","position":{"x": 1750,"y": 350,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Position largage 2","command":"face#1750;0","position":{"x": 1750,"y": 350,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Depilement 2","command":"action#15","position":{"x": 1750,"y": 350,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Position largage 3","command":"goto-back#1750;450","position":{"x": 1750,"y": 450,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Position largage 3","command":"face#1750;0","position":{"x": 1750,"y": 450,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Depilement 3","command":"action#15","position":{"x": 1750,"y": 450,"theta": -1.5707963267948966}},{ "task":"Libération assiette 4","command":"goto-back#1750;600","position":{"x": 1750,"y": 600,"theta": -1.5707963267948966}},{ "task":"Libération assiette 4","command":"goto#1600;600","position":{"x": 1600,"y": 600,"theta": 3.141592653589793}},{ "task":"Rangement pince","command":"action#7","position":{"x": 1600,"y": 600,"theta": 3.141592653589793}},{ "task":"Rangement pince","command":"action#2","position":{"x": 1600,"y": 600,"theta": 3.141592653589793}},{ "task":"Go attente assiette 1","command":"goto-astar#1600;600","position":{"x": 1600,"y": 600,"theta": 3.141592653589793}},{ "task":"Go attente assiette 1","command":"goto-astar#1610;610","position":{"x": 1610,"y": 610,"theta": 0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#1610;880","position":{"x": 1610,"y": 880,"theta": 1.5707963267948966}},{ "task":"Go attente assiette 1","command":"goto-astar#1740;1010","position":{"x": 1740,"y": 1010,"theta": 0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#2380;1010","position":{"x": 2380,"y": 1010,"theta": 0.0}},{ "task":"Go attente assiette 1","command":"goto-astar#2550;1180","position":{"x": 2550,"y": 1180,"theta": 0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#2580;1180","position":{"x": 2580,"y": 1180,"theta": 0.0}},{ "task":"Go attente assiette 1","command":"goto-astar#2700;1300","position":{"x": 2700,"y": 1300,"theta": 0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#2800;1300","position":{"x": 2800,"y": 1300,"theta": 0.0}},{ "task":"Wait 90s","command":"wait_chrono#0","position":{"x": 2800,"y": 1300,"theta": 0.0}},{ "task":"Go assiette 1","command":"goto#2550;1500","position":{"x": 2550,"y": 1500,"theta": 2.4668517113662403}}]
;var strategyBig3000 = [{ "task":"Position de départ","command":"start","position":{"x": 2720,"y": 225,"theta": 3.141592653589793}},{ "task":"Step de départ bizarre","command":"go#1","position":{"x": 2719,"y": 225,"theta": 3.141592653589793}},{ "task":"Init pince","command":"action#2","position":{"x": 2719,"y": 225,"theta": 3.141592653589793}},{ "task":"Init ascenceur","command":"action#10","position":{"x": 2719,"y": 225,"theta": 3.141592653589793}},{ "task":"Récupération violet 2","command":"goto#2630;225","position":{"x": 2630,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise violet 2","command":"action#13","position":{"x": 2630,"y": 225,"theta": 3.141592653589793}},{ "task":"Libération violet 2","command":"delete-zone#west_cake_purple_2","position":{"x": 2630,"y": 225,"theta": 3.141592653589793}},{ "task":"Récupération jaune 2","command":"goto#2430;225","position":{"x": 2430,"y": 225,"theta": 3.141592653589793}},{ "task":"Récupération jaune 2","command":"face#0;225","position":{"x": 2430,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#2","position":{"x": 2430,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"goto-back#2450;225","position":{"x": 2450,"y": 225,"theta": -3.141592653589793}},{ "task":"Prise jaune 2","command":"action#8","position":{"x": 2450,"y": 225,"theta": -3.141592653589793}},{ "task":"Prise jaune 2","command":"action#9","position":{"x": 2450,"y": 225,"theta": -3.141592653589793}},{ "task":"Prise jaune 2","command":"goto#2420;225","position":{"x": 2420,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#0","position":{"x": 2420,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#7","position":{"x": 2420,"y": 225,"theta": 3.141592653589793}},{ "task":"Libération jaune 2","command":"delete-zone#west_cake_yellow_2","position":{"x": 2420,"y": 225,"theta": 3.141592653589793}},{ "task":"Go assiette 3","command":"goto-astar#2420;220","position":{"x": 2420,"y": 220,"theta": -1.5707963267948966}},{ "task":"Go assiette 3","command":"goto-astar#2190;450","position":{"x": 2190,"y": 450,"theta": 2.356194490192345}},{ "task":"Go assiette 3","command":"goto-astar#1100;450","position":{"x": 1100,"y": 450,"theta": 3.141592653589793}},{ "task":"Alignement assiette 3","command":"face#1100;0","position":{"x": 1100,"y": 450,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 1","command":"goto#1100;250","position":{"x": 1100,"y": 250,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 1","command":"face#1100;0","position":{"x": 1100,"y": 250,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Depilement 1","command":"action#15","position":{"x": 1100,"y": 250,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 2","command":"goto-back#1100;350","position":{"x": 1100,"y": 350,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 2","command":"face#1100;0","position":{"x": 1100,"y": 350,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Depilement 2","command":"action#15","position":{"x": 1100,"y": 350,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 3","command":"goto-back#1100;450","position":{"x": 1100,"y": 450,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 3","command":"face#1100;0","position":{"x": 1100,"y": 450,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Depilement 3","command":"action#15","position":{"x": 1100,"y": 450,"theta": -1.5707963267948966}},{ "task":"Libération assiette 3","command":"goto-back#1100;650","position":{"x": 1100,"y": 650,"theta": -1.5707963267948966}},{ "task":"Blocage assiette 3","command":"add-zone#start3000_3","position":{"x": 1100,"y": 650,"theta": -1.5707963267948966}},{ "task":"Libération brun 1","command":"delete-zone#west_cake_brown_1","position":{"x": 1100,"y": 650,"theta": -1.5707963267948966}},{ "task":"Go assiette 4","command":"goto-astar#1100;650","position":{"x": 1100,"y": 650,"theta": -1.5707963267948966}},{ "task":"Go assiette 4","command":"goto-astar#1610;1160","position":{"x": 1610,"y": 1160,"theta": 0.7853981633974483}},{ "task":"Go assiette 4","command":"goto-astar#1610;1480","position":{"x": 1610,"y": 1480,"theta": 1.5707963267948966}},{ "task":"Go assiette 4","command":"goto-astar#1680;1550","position":{"x": 1680,"y": 1550,"theta": 0.7853981633974483}},{ "task":"Go assiette 4","command":"goto-astar#1750;1550","position":{"x": 1750,"y": 1550,"theta": 0.0}},{ "task":"Alignement assiette 4","command":"face#1750;2000","position":{"x": 1750,"y": 1550,"theta": 1.5707963267948966}},{ "task":"Assiette 4 - Position largage 1","command":"goto#1750;1750","position":{"x": 1750,"y": 1750,"theta": 1.5707963267948966}},{ "task":"Assiette 4 - Position largage 1","command":"face#1750;2000","position":{"x": 1750,"y": 1750,"theta": 1.5707963267948966}},{ "task":"Assiette 4 - Depilement 1","command":"action#15","position":{"x": 1750,"y": 1750,"theta": 1.5707963267948966}},{ "task":"Assiette 4 - Position largage 2","command":"goto-back#1750;1650","position":{"x": 1750,"y": 1650,"theta": -4.71238898038469}},{ "task":"Assiette 4 - Position largage 2","command":"face#1750;2000","position":{"x": 1750,"y": 1650,"theta": 1.5707963267948966}},{ "task":"Assiette 4 - Depilement 2","command":"action#15","position":{"x": 1750,"y": 1650,"theta": 1.5707963267948966}},{ "task":"Assiette 4 - Position largage 3","command":"goto-back#1750;1550","position":{"x": 1750,"y": 1550,"theta": -4.71238898038469}},{ "task":"Assiette 4 - Position largage 3","command":"face#1750;2000","position":{"x": 1750,"y": 1550,"theta": 1.5707963267948966}},{ "task":"Assiette 4 - Depilement 3","command":"action#15","position":{"x": 1750,"y": 1550,"theta": 1.5707963267948966}},{ "task":"Libération assiette 4","command":"goto-back#1750;1400","position":{"x": 1750,"y": 1400,"theta": -4.71238898038469}},{ "task":"Libération assiette 4","command":"goto#1600;1400","position":{"x": 1600,"y": 1400,"theta": 3.141592653589793}},{ "task":"Rangement pince","command":"action#7","position":{"x": 1600,"y": 1400,"theta": 3.141592653589793}},{ "task":"Rangement pince","command":"action#2","position":{"x": 1600,"y": 1400,"theta": 3.141592653589793}},{ "task":"Go attente assiette 1","command":"goto-astar#1600;1400","position":{"x": 1600,"y": 1400,"theta": 3.141592653589793}},{ "task":"Go attente assiette 1","command":"goto-astar#1610;1390","position":{"x": 1610,"y": 1390,"theta": -0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#1610;1160","position":{"x": 1610,"y": 1160,"theta": -1.5707963267948966}},{ "task":"Go attente assiette 1","command":"goto-astar#1640;1130","position":{"x": 1640,"y": 1130,"theta": -0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#1640;1110","position":{"x": 1640,"y": 1110,"theta": -1.5707963267948966}},{ "task":"Go attente assiette 1","command":"goto-astar#1770;980","position":{"x": 1770,"y": 980,"theta": -0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#2090;980","position":{"x": 2090,"y": 980,"theta": 0.0}},{ "task":"Go attente assiette 1","command":"goto-astar#2370;700","position":{"x": 2370,"y": 700,"theta": -0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#2800;700","position":{"x": 2800,"y": 700,"theta": 0.0}},{ "task":"Wait 90s","command":"wait_chrono#0","position":{"x": 2800,"y": 700,"theta": 0.0}},{ "task":"Go assiette 1","command":"goto#2550;500","position":{"x": 2550,"y": 500,"theta": 3.816333595813346}}]
;var strategySmall0 = [{ "task":"Position de départ","command":"start","position":{"x": 2920,"y": 1775,"theta": 0.0}},{ "task":"On recule pour souffler","command":"go#-200","position":{"x": 2720,"y": 1775,"theta": 0.0}},{ "task":"Alignement tir","command":"face#3000;1775","position":{"x": 2720,"y": 1775,"theta": 0.0}},{ "task":"Souffler bouboule","command":"action#38","position":{"x": 2720,"y": 1775,"theta": 0.0}},{ "task":"On recule","command":"goto-back#2450;1775","position":{"x": 2450,"y": 1775,"theta": 0.0}},{ "task":"Libération violet 2","command":"delete-zone#east_cake_purple_2","position":{"x": 2450,"y": 1775,"theta": 0.0}},{ "task":"Libération jaune 2","command":"delete-zone#east_cake_yellow_2","position":{"x": 2450,"y": 1775,"theta": 0.0}},{ "task":"Libération brun 2","command":"delete-zone#east_cake_brown_2","position":{"x": 2450,"y": 1775,"theta": 0.0}},{ "task":"Libération brun 1","command":"delete-zone#east_cake_brown_1","position":{"x": 2450,"y": 1775,"theta": 0.0}},{ "task":"Position aspiration","command":"goto-astar#2450;1770","position":{"x": 2450,"y": 1770,"theta": -1.5707963267948966}},{ "task":"Position aspiration","command":"goto-astar#2650;1570","position":{"x": 2650,"y": 1570,"theta": -0.7853981633974483}},{ "task":"Position aspiration","command":"goto-astar#2650;1170","position":{"x": 2650,"y": 1170,"theta": -1.5707963267948966}},{ "task":"Position aspiration","command":"face#3000;1170","position":{"x": 2650,"y": 1170,"theta": 0.0}},{ "task":"Aspiration cherry bouboule sud","command":"action#35","position":{"x": 2650,"y": 1170,"theta": 0.0}},{ "task":"Aspiration cherry bouboule sud","command":"action#27","position":{"x": 2650,"y": 1170,"theta": 0.0}},{ "task":"Reduction de vitesse","command":"speed#25","position":{"x": 2650,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2680;1170","position":{"x": 2680,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1170","position":{"x": 2680,"y": 1170,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2680,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2710;1170","position":{"x": 2710,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1170","position":{"x": 2710,"y": 1170,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2710,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2740;1170","position":{"x": 2740,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1170","position":{"x": 2740,"y": 1170,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2740,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2770;1170","position":{"x": 2770,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1170","position":{"x": 2770,"y": 1170,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2770,"y": 1170,"theta": 0.0}},{ "task":"Stockage bouboule sud","command":"action#40","position":{"x": 2770,"y": 1170,"theta": 0.0}},{ "task":"Aspiration cherry bouboule sud","command":"action#35","position":{"x": 2770,"y": 1170,"theta": 0.0}},{ "task":"Aspiration cherry bouboule sud","command":"action#27","position":{"x": 2770,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2800;1170","position":{"x": 2800,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1170","position":{"x": 2800,"y": 1170,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2800,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2830;1170","position":{"x": 2830,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1170","position":{"x": 2830,"y": 1170,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2830,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2860;1170","position":{"x": 2860,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1170","position":{"x": 2860,"y": 1170,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2860,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2890;1170","position":{"x": 2890,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1170","position":{"x": 2890,"y": 1170,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2890,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2920;1170","position":{"x": 2920,"y": 1170,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1170","position":{"x": 2920,"y": 1170,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2920,"y": 1170,"theta": 0.0}},{ "task":"Fin aspiration sud","command":"action#29","position":{"x": 2920,"y": 1170,"theta": 0.0}},{ "task":"Fin aspiration sud","command":"action#33","position":{"x": 2920,"y": 1170,"theta": 0.0}},{ "task":"Vitesse normale","command":"speed#100","position":{"x": 2920,"y": 1170,"theta": 0.0}},{ "task":"Sortie aspiration","command":"goto-back#2600;1170","position":{"x": 2600,"y": 1170,"theta": 0.0}},{ "task":"Position vidage bouboule","command":"goto-astar#2600;1170","position":{"x": 2600,"y": 1170,"theta": 0.0}},{ "task":"Position vidage bouboule","command":"goto-astar#2800;1370","position":{"x": 2800,"y": 1370,"theta": 0.7853981633974483}},{ "task":"Position vidage bouboule","command":"goto-astar#2800;1775","position":{"x": 2800,"y": 1775,"theta": 1.5707963267948966}},{ "task":"Alignement vidage bouboule","command":"face#3000;1775","position":{"x": 2800,"y": 1775,"theta": 0.0}},{ "task":"Position vomie","command":"go#100","position":{"x": 2900,"y": 1775,"theta": 0.0}},{ "task":"Sortie vomie","command":"goto-back#2800;1775","position":{"x": 2800,"y": 1775,"theta": 0.0}},{ "task":"Vomie sud 1","command":"action#39","position":{"x": 2800,"y": 1775,"theta": 0.0}},{ "task":"Vomie sud reserve","command":"action#41","position":{"x": 2800,"y": 1775,"theta": 0.0}},{ "task":"Vomie sud 2","command":"action#39","position":{"x": 2800,"y": 1775,"theta": 0.0}}]
;var strategySmall3000 = [{ "task":"Position de départ","command":"start","position":{"x": 2920,"y": 225,"theta": 0.0}},{ "task":"On recule pour souffler","command":"go#-200","position":{"x": 2720,"y": 225,"theta": 0.0}},{ "task":"Alignement tir","command":"face#3000;225","position":{"x": 2720,"y": 225,"theta": 0.0}},{ "task":"Souffler bouboule","command":"action#38","position":{"x": 2720,"y": 225,"theta": 0.0}},{ "task":"On recule","command":"goto-back#2450;225","position":{"x": 2450,"y": 225,"theta": 0.0}},{ "task":"Libération violet 2","command":"delete-zone#west_cake_purple_2","position":{"x": 2450,"y": 225,"theta": 0.0}},{ "task":"Libération jaune 2","command":"delete-zone#west_cake_yellow_2","position":{"x": 2450,"y": 225,"theta": 0.0}},{ "task":"Libération brun 2","command":"delete-zone#west_cake_brown_2","position":{"x": 2450,"y": 225,"theta": 0.0}},{ "task":"Libération brun 1","command":"delete-zone#west_cake_brown_1","position":{"x": 2450,"y": 225,"theta": 0.0}},{ "task":"Position aspiration","command":"goto-astar#2450;220","position":{"x": 2450,"y": 220,"theta": -1.5707963267948966}},{ "task":"Position aspiration","command":"goto-astar#2650;420","position":{"x": 2650,"y": 420,"theta": 0.7853981633974483}},{ "task":"Position aspiration","command":"goto-astar#2650;830","position":{"x": 2650,"y": 830,"theta": 1.5707963267948966}},{ "task":"Position aspiration","command":"face#3000;830","position":{"x": 2650,"y": 830,"theta": 0.0}},{ "task":"Aspiration cherry bouboule sud","command":"action#35","position":{"x": 2650,"y": 830,"theta": 0.0}},{ "task":"Aspiration cherry bouboule sud","command":"action#28","position":{"x": 2650,"y": 830,"theta": 0.0}},{ "task":"Reduction de vitesse","command":"speed#25","position":{"x": 2650,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2680;830","position":{"x": 2680,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;830","position":{"x": 2680,"y": 830,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2680,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2710;830","position":{"x": 2710,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;830","position":{"x": 2710,"y": 830,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2710,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2740;830","position":{"x": 2740,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;830","position":{"x": 2740,"y": 830,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2740,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2770;830","position":{"x": 2770,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;830","position":{"x": 2770,"y": 830,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2770,"y": 830,"theta": 0.0}},{ "task":"Stockage bouboule sud","command":"action#40","position":{"x": 2770,"y": 830,"theta": 0.0}},{ "task":"Aspiration cherry bouboule sud","command":"action#35","position":{"x": 2770,"y": 830,"theta": 0.0}},{ "task":"Aspiration cherry bouboule sud","command":"action#28","position":{"x": 2770,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2800;830","position":{"x": 2800,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;830","position":{"x": 2800,"y": 830,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2800,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2830;830","position":{"x": 2830,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;830","position":{"x": 2830,"y": 830,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2830,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2860;830","position":{"x": 2860,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;830","position":{"x": 2860,"y": 830,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2860,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2890;830","position":{"x": 2890,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;830","position":{"x": 2890,"y": 830,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2890,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2920;830","position":{"x": 2920,"y": 830,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;830","position":{"x": 2920,"y": 830,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2920,"y": 830,"theta": 0.0}},{ "task":"Fin aspiration sud","command":"action#29","position":{"x": 2920,"y": 830,"theta": 0.0}},{ "task":"Fin aspiration sud","command":"action#33","position":{"x": 2920,"y": 830,"theta": 0.0}},{ "task":"Vitesse normale","command":"speed#100","position":{"x": 2920,"y": 830,"theta": 0.0}},{ "task":"Sortie aspiration","command":"goto-back#2600;830","position":{"x": 2600,"y": 830,"theta": 0.0}},{ "task":"Position vidage bouboule","command":"goto-astar#2600;830","position":{"x": 2600,"y": 830,"theta": 0.0}},{ "task":"Position vidage bouboule","command":"goto-astar#2800;630","position":{"x": 2800,"y": 630,"theta": -0.7853981633974483}},{ "task":"Position vidage bouboule","command":"goto-astar#2800;225","position":{"x": 2800,"y": 225,"theta": -1.5707963267948966}},{ "task":"Alignement vidage bouboule","command":"face#3000;225","position":{"x": 2800,"y": 225,"theta": 0.0}},{ "task":"Position vomie","command":"go#100","position":{"x": 2900,"y": 225,"theta": 0.0}},{ "task":"Sortie vomie","command":"goto-back#2800;225","position":{"x": 2800,"y": 225,"theta": 0.0}},{ "task":"Vomie sud 1","command":"action#39","position":{"x": 2800,"y": 225,"theta": 0.0}},{ "task":"Vomie sud reserve","command":"action#41","position":{"x": 2800,"y": 225,"theta": 0.0}},{ "task":"Vomie sud 2","command":"action#39","position":{"x": 2800,"y": 225,"theta": 0.0}}]
;
