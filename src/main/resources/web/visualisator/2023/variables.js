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
};var strategyBig0 = [{ "task":"Position de départ","command":"start","position":{"x": 2720,"y": 1775,"theta": 3.141592653589793}},{ "task":"Step de départ bizarre","command":"go#1","position":{"x": 2719,"y": 1775,"theta": 3.141592653589793}},{ "task":"Récupération violet 2","command":"goto#2630;1775","position":{"x": 2630,"y": 1775,"theta": 3.141592653589793}},{ "task":"Libération violet 2","command":"delete-zone#east_cake_purple_2","position":{"x": 2630,"y": 1775,"theta": 3.141592653589793}},{ "task":"Récupération jaune 2","command":"goto#2430;1775","position":{"x": 2430,"y": 1775,"theta": 3.141592653589793}},{ "task":"Récupération jaune 2","command":"face#0;1775","position":{"x": 2430,"y": 1775,"theta": 3.141592653589793}},{ "task":"Libération jaune 2","command":"delete-zone#east_cake_yellow_2","position":{"x": 2430,"y": 1775,"theta": 3.141592653589793}},{ "task":"Récupération brun 2","command":"goto-astar#2430;1770","position":{"x": 2430,"y": 1770,"theta": -1.5707963267948966}},{ "task":"Récupération brun 2","command":"goto-astar#2200;1540","position":{"x": 2200,"y": 1540,"theta": 3.9269908169872414}},{ "task":"Récupération brun 2","command":"goto-astar#2200;1340","position":{"x": 2200,"y": 1340,"theta": -1.5707963267948966}},{ "task":"Récupération brun 2","command":"goto#2200;1275","position":{"x": 2200,"y": 1275,"theta": -1.5707963267948966}},{ "task":"Récupération brun 2","command":"face#0;1275","position":{"x": 2200,"y": 1275,"theta": 3.141592653589793}},{ "task":"Récupération brun 2","command":"goto#2080;1275","position":{"x": 2080,"y": 1275,"theta": 3.141592653589793}},{ "task":"Récupération brun 2","command":"face#0;1275","position":{"x": 2080,"y": 1275,"theta": 3.141592653589793}},{ "task":"Libération brun 2","command":"delete-zone#east_cake_brown_2","position":{"x": 2080,"y": 1275,"theta": 3.141592653589793}},{ "task":"Récupération brun 1","command":"goto-astar#2080;1270","position":{"x": 2080,"y": 1270,"theta": -1.5707963267948966}},{ "task":"Récupération brun 1","command":"goto-astar#2070;1280","position":{"x": 2070,"y": 1280,"theta": 2.356194490192345}},{ "task":"Récupération brun 1","command":"goto-astar#1400;1280","position":{"x": 1400,"y": 1280,"theta": 3.141592653589793}},{ "task":"Libération brun 1","command":"delete-zone#east_cake_brown_1","position":{"x": 1400,"y": 1280,"theta": 3.141592653589793}},{ "task":"Récupération brun 1","command":"goto#1100;1280","position":{"x": 1100,"y": 1280,"theta": 3.141592653589793}},{ "task":"Go assiette 2","command":"goto-astar#1100;1280","position":{"x": 1100,"y": 1280,"theta": 3.141592653589793}},{ "task":"Go assiette 2","command":"goto-astar#980;1400","position":{"x": 980,"y": 1400,"theta": 2.356194490192345}},{ "task":"Go assiette 2","command":"goto-astar#800;1400","position":{"x": 800,"y": 1400,"theta": 3.141592653589793}},{ "task":"Alignement assiette 2","command":"face#0;1400","position":{"x": 800,"y": 1400,"theta": 3.141592653589793}},{ "task":"Assiette 2 - Position trash","command":"goto#500;1400","position":{"x": 500,"y": 1400,"theta": 3.141592653589793}},{ "task":"Libération assiette 2","command":"goto-back#800;1400","position":{"x": 800,"y": 1400,"theta": -3.141592653589793}},{ "task":"Go assiette 2","command":"goto-astar#800;1400","position":{"x": 800,"y": 1400,"theta": -3.141592653589793}},{ "task":"Go assiette 2","command":"goto-astar#640;1240","position":{"x": 640,"y": 1240,"theta": 3.9269908169872414}},{ "task":"Go assiette 2","command":"goto-astar#500;1240","position":{"x": 500,"y": 1240,"theta": 3.141592653589793}},{ "task":"Alignement assiette 2","command":"face#0;1240","position":{"x": 500,"y": 1240,"theta": 3.141592653589793}},{ "task":"Assiette 2 - Position largage 1","command":"goto#250;1240","position":{"x": 250,"y": 1240,"theta": 3.141592653589793}},{ "task":"Assiette 2 - Position largage 1","command":"face#0;1240","position":{"x": 250,"y": 1240,"theta": 3.141592653589793}},{ "task":"Assiette 2 - Position largage 2","command":"goto-back#350;1240","position":{"x": 350,"y": 1240,"theta": -3.141592653589793}},{ "task":"Assiette 2 - Position largage 2","command":"face#0;1240","position":{"x": 350,"y": 1240,"theta": 3.141592653589793}},{ "task":"Assiette 2 - Position largage 3","command":"goto-back#450;1240","position":{"x": 450,"y": 1240,"theta": -3.141592653589793}},{ "task":"Assiette 2 - Position largage 3","command":"face#0;1240","position":{"x": 450,"y": 1240,"theta": 3.141592653589793}},{ "task":"Libération assiette 2","command":"goto-back#650;1240","position":{"x": 650,"y": 1240,"theta": -3.141592653589793}},{ "task":"Go assiette 3","command":"goto-astar#650;1240","position":{"x": 650,"y": 1240,"theta": -3.141592653589793}},{ "task":"Go assiette 3","command":"goto-astar#960;1550","position":{"x": 960,"y": 1550,"theta": 0.7853981633974483}},{ "task":"Go assiette 3","command":"goto-astar#1100;1550","position":{"x": 1100,"y": 1550,"theta": 0.0}},{ "task":"Alignement assiette 3","command":"face#1100;2000","position":{"x": 1100,"y": 1550,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Position largage 1","command":"goto#1100;1750","position":{"x": 1100,"y": 1750,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Position largage 1","command":"face#1100;2000","position":{"x": 1100,"y": 1750,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Position largage 2","command":"goto-back#1100;1650","position":{"x": 1100,"y": 1650,"theta": -4.71238898038469}},{ "task":"Assiette 3 - Position largage 2","command":"face#1100;2000","position":{"x": 1100,"y": 1650,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Position largage 3","command":"goto-back#1100;1550","position":{"x": 1100,"y": 1550,"theta": -4.71238898038469}},{ "task":"Assiette 3 - Position largage 3","command":"face#1100;2000","position":{"x": 1100,"y": 1550,"theta": 1.5707963267948966}},{ "task":"Libération assiette 3","command":"goto-back#1100;1450","position":{"x": 1100,"y": 1450,"theta": -4.71238898038469}},{ "task":"Blocage assiette 3","command":"add-zone#start0_3","position":{"x": 1100,"y": 1450,"theta": -4.71238898038469}},{ "task":"Alignement assiette 4","command":"face#1750;0","position":{"x": 1100,"y": 1450,"theta": -1.1493771199070923}},{ "task":"Assiette 4 - Position largage 1","command":"goto#1750;250","position":{"x": 1750,"y": 250,"theta": -1.0743735733900148}},{ "task":"Assiette 4 - Position largage 1","command":"face#1750;0","position":{"x": 1750,"y": 250,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Position largage 2","command":"goto-back#1750;350","position":{"x": 1750,"y": 350,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Position largage 2","command":"face#1750;0","position":{"x": 1750,"y": 350,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Position largage 3","command":"goto-back#1750;450","position":{"x": 1750,"y": 450,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Position largage 3","command":"face#1750;0","position":{"x": 1750,"y": 450,"theta": -1.5707963267948966}},{ "task":"Libération assiette 4","command":"goto-back#1750;600","position":{"x": 1750,"y": 600,"theta": -1.5707963267948966}},{ "task":"Libération assiette 4","command":"goto#1600;600","position":{"x": 1600,"y": 600,"theta": 3.141592653589793}},{ "task":"Go attente assiette 1","command":"goto-astar#1600;600","position":{"x": 1600,"y": 600,"theta": 3.141592653589793}},{ "task":"Go attente assiette 1","command":"goto-astar#1610;610","position":{"x": 1610,"y": 610,"theta": 0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#1610;910","position":{"x": 1610,"y": 910,"theta": 1.5707963267948966}},{ "task":"Go attente assiette 1","command":"goto-astar#2090;1390","position":{"x": 2090,"y": 1390,"theta": 0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#2540;1390","position":{"x": 2540,"y": 1390,"theta": 0.0}},{ "task":"Go attente assiette 1","command":"goto-astar#2550;1400","position":{"x": 2550,"y": 1400,"theta": 0.7853981633974483}},{ "task":"Wait 90s","command":"wait_chrono#0","position":{"x": 2550,"y": 1400,"theta": 0.7853981633974483}},{ "task":"Go assiette 1","command":"goto#2550;1500","position":{"x": 2550,"y": 1500,"theta": 1.5707963267948966}}]
;var strategyBig3000 = [{ "task":"Position de départ","command":"start","position":{"x": 2720,"y": 225,"theta": 3.141592653589793}},{ "task":"Step de départ bizarre","command":"go#1","position":{"x": 2719,"y": 225,"theta": 3.141592653589793}},{ "task":"Récupération violet 2","command":"goto#2630;225","position":{"x": 2630,"y": 225,"theta": 3.141592653589793}},{ "task":"Libération violet 2","command":"delete-zone#west_cake_purple_2","position":{"x": 2630,"y": 225,"theta": 3.141592653589793}},{ "task":"Récupération jaune 2","command":"goto#2430;225","position":{"x": 2430,"y": 225,"theta": 3.141592653589793}},{ "task":"Récupération jaune 2","command":"face#0;225","position":{"x": 2430,"y": 225,"theta": 3.141592653589793}},{ "task":"Libération jaune 2","command":"delete-zone#west_cake_yellow_2","position":{"x": 2430,"y": 225,"theta": 3.141592653589793}},{ "task":"Récupération brun 2","command":"goto-astar#2430;220","position":{"x": 2430,"y": 220,"theta": -1.5707963267948966}},{ "task":"Récupération brun 2","command":"goto-astar#2200;450","position":{"x": 2200,"y": 450,"theta": 2.356194490192345}},{ "task":"Récupération brun 2","command":"goto-astar#2200;660","position":{"x": 2200,"y": 660,"theta": 1.5707963267948966}},{ "task":"Récupération brun 2","command":"goto#2200;725","position":{"x": 2200,"y": 725,"theta": 1.5707963267948966}},{ "task":"Récupération brun 2","command":"face#0;725","position":{"x": 2200,"y": 725,"theta": 3.141592653589793}},{ "task":"Récupération brun 2","command":"goto#2080;725","position":{"x": 2080,"y": 725,"theta": 3.141592653589793}},{ "task":"Récupération brun 2","command":"face#0;725","position":{"x": 2080,"y": 725,"theta": 3.141592653589793}},{ "task":"Libération brun 2","command":"delete-zone#west_cake_brown_2","position":{"x": 2080,"y": 725,"theta": 3.141592653589793}},{ "task":"Récupération brun 1","command":"goto-astar#2080;720","position":{"x": 2080,"y": 720,"theta": -1.5707963267948966}},{ "task":"Récupération brun 1","command":"goto-astar#1400;720","position":{"x": 1400,"y": 720,"theta": 3.141592653589793}},{ "task":"Libération brun 1","command":"delete-zone#west_cake_brown_1","position":{"x": 1400,"y": 720,"theta": 3.141592653589793}},{ "task":"Récupération brun 1","command":"goto#1100;720","position":{"x": 1100,"y": 720,"theta": 3.141592653589793}},{ "task":"Go assiette 2","command":"goto-astar#1100;720","position":{"x": 1100,"y": 720,"theta": 3.141592653589793}},{ "task":"Go assiette 2","command":"goto-astar#980;600","position":{"x": 980,"y": 600,"theta": 3.9269908169872414}},{ "task":"Go assiette 2","command":"goto-astar#800;600","position":{"x": 800,"y": 600,"theta": 3.141592653589793}},{ "task":"Alignement assiette 2","command":"face#0;600","position":{"x": 800,"y": 600,"theta": 3.141592653589793}},{ "task":"Assiette 2 - Position trash","command":"goto#500;600","position":{"x": 500,"y": 600,"theta": 3.141592653589793}},{ "task":"Libération assiette 2","command":"goto-back#800;600","position":{"x": 800,"y": 600,"theta": -3.141592653589793}},{ "task":"Go assiette 2","command":"goto-astar#800;600","position":{"x": 800,"y": 600,"theta": -3.141592653589793}},{ "task":"Go assiette 2","command":"goto-astar#640;760","position":{"x": 640,"y": 760,"theta": 2.356194490192345}},{ "task":"Go assiette 2","command":"goto-astar#500;760","position":{"x": 500,"y": 760,"theta": 3.141592653589793}},{ "task":"Alignement assiette 2","command":"face#0;760","position":{"x": 500,"y": 760,"theta": 3.141592653589793}},{ "task":"Assiette 2 - Position largage 1","command":"goto#250;760","position":{"x": 250,"y": 760,"theta": 3.141592653589793}},{ "task":"Assiette 2 - Position largage 1","command":"face#0;760","position":{"x": 250,"y": 760,"theta": 3.141592653589793}},{ "task":"Assiette 2 - Position largage 2","command":"goto-back#350;760","position":{"x": 350,"y": 760,"theta": -3.141592653589793}},{ "task":"Assiette 2 - Position largage 2","command":"face#0;760","position":{"x": 350,"y": 760,"theta": 3.141592653589793}},{ "task":"Assiette 2 - Position largage 3","command":"goto-back#450;760","position":{"x": 450,"y": 760,"theta": -3.141592653589793}},{ "task":"Assiette 2 - Position largage 3","command":"face#0;760","position":{"x": 450,"y": 760,"theta": 3.141592653589793}},{ "task":"Libération assiette 2","command":"goto-back#650;760","position":{"x": 650,"y": 760,"theta": -3.141592653589793}},{ "task":"Go assiette 3","command":"goto-astar#650;760","position":{"x": 650,"y": 760,"theta": -3.141592653589793}},{ "task":"Go assiette 3","command":"goto-astar#960;450","position":{"x": 960,"y": 450,"theta": -0.7853981633974483}},{ "task":"Go assiette 3","command":"goto-astar#1100;450","position":{"x": 1100,"y": 450,"theta": 0.0}},{ "task":"Alignement assiette 3","command":"face#1100;0","position":{"x": 1100,"y": 450,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 1","command":"goto#1100;250","position":{"x": 1100,"y": 250,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 1","command":"face#1100;0","position":{"x": 1100,"y": 250,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 2","command":"goto-back#1100;350","position":{"x": 1100,"y": 350,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 2","command":"face#1100;0","position":{"x": 1100,"y": 350,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 3","command":"goto-back#1100;450","position":{"x": 1100,"y": 450,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 3","command":"face#1100;0","position":{"x": 1100,"y": 450,"theta": -1.5707963267948966}},{ "task":"Libération assiette 3","command":"goto-back#1100;550","position":{"x": 1100,"y": 550,"theta": -1.5707963267948966}},{ "task":"Blocage assiette 3","command":"add-zone#start3000_3","position":{"x": 1100,"y": 550,"theta": -1.5707963267948966}},{ "task":"Alignement assiette 4","command":"face#1750;2000","position":{"x": 1100,"y": 550,"theta": 1.1493771199070923}},{ "task":"Assiette 4 - Position largage 1","command":"goto#1750;1750","position":{"x": 1750,"y": 1750,"theta": 1.0743735733900148}},{ "task":"Assiette 4 - Position largage 1","command":"face#1750;2000","position":{"x": 1750,"y": 1750,"theta": 1.5707963267948966}},{ "task":"Assiette 4 - Position largage 2","command":"goto-back#1750;1650","position":{"x": 1750,"y": 1650,"theta": -4.71238898038469}},{ "task":"Assiette 4 - Position largage 2","command":"face#1750;2000","position":{"x": 1750,"y": 1650,"theta": 1.5707963267948966}},{ "task":"Assiette 4 - Position largage 3","command":"goto-back#1750;1550","position":{"x": 1750,"y": 1550,"theta": -4.71238898038469}},{ "task":"Assiette 4 - Position largage 3","command":"face#1750;2000","position":{"x": 1750,"y": 1550,"theta": 1.5707963267948966}},{ "task":"Libération assiette 4","command":"goto-back#1750;1400","position":{"x": 1750,"y": 1400,"theta": -4.71238898038469}},{ "task":"Libération assiette 4","command":"goto#1600;1400","position":{"x": 1600,"y": 1400,"theta": 3.141592653589793}},{ "task":"Go attente assiette 1","command":"goto-astar#1600;1400","position":{"x": 1600,"y": 1400,"theta": 3.141592653589793}},{ "task":"Go attente assiette 1","command":"goto-astar#1610;1390","position":{"x": 1610,"y": 1390,"theta": -0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#1610;1100","position":{"x": 1610,"y": 1100,"theta": -1.5707963267948966}},{ "task":"Go attente assiette 1","command":"goto-astar#2110;600","position":{"x": 2110,"y": 600,"theta": -0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#2550;600","position":{"x": 2550,"y": 600,"theta": 0.0}},{ "task":"Wait 90s","command":"wait_chrono#0","position":{"x": 2550,"y": 600,"theta": 0.0}},{ "task":"Go assiette 1","command":"goto#2550;500","position":{"x": 2550,"y": 500,"theta": -1.5707963267948966}}]
;var strategySmall0 = [{ "task":"Position de départ","command":"start","position":{"x": 2900,"y": 1775,"theta": 0.0}},{ "task":"On quitte le bord","command":"go#-250","position":{"x": 2650,"y": 1775,"theta": 0.0}},{ "task":"Libération violet 2","command":"delete-zone#east_cake_purple_2","position":{"x": 2650,"y": 1775,"theta": 0.0}},{ "task":"Libération jaune 2","command":"delete-zone#east_cake_yellow_2","position":{"x": 2650,"y": 1775,"theta": 0.0}},{ "task":"Libération brun 2","command":"delete-zone#east_cake_brown_2","position":{"x": 2650,"y": 1775,"theta": 0.0}},{ "task":"Libération brun 1","command":"delete-zone#east_cake_brown_1","position":{"x": 2650,"y": 1775,"theta": 0.0}},{ "task":"Position aspiration","command":"goto-astar#2650;1770","position":{"x": 2650,"y": 1770,"theta": -1.5707963267948966}},{ "task":"Position aspiration","command":"goto-astar#2650;1180","position":{"x": 2650,"y": 1180,"theta": -1.5707963267948966}},{ "task":"Position aspiration","command":"face#3000;1180","position":{"x": 2650,"y": 1180,"theta": 0.0}},{ "task":"Reduction de vitesse","command":"speed#25","position":{"x": 2650,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2680;1180","position":{"x": 2680,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1180","position":{"x": 2680,"y": 1180,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2680,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2710;1180","position":{"x": 2710,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1180","position":{"x": 2710,"y": 1180,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2710,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2740;1180","position":{"x": 2740,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1180","position":{"x": 2740,"y": 1180,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2740,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2770;1180","position":{"x": 2770,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1180","position":{"x": 2770,"y": 1180,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2770,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2800;1180","position":{"x": 2800,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1180","position":{"x": 2800,"y": 1180,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2800,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2830;1180","position":{"x": 2830,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1180","position":{"x": 2830,"y": 1180,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2830,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2860;1180","position":{"x": 2860,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1180","position":{"x": 2860,"y": 1180,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2860,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2890;1180","position":{"x": 2890,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1180","position":{"x": 2890,"y": 1180,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2890,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2920;1180","position":{"x": 2920,"y": 1180,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1180","position":{"x": 2920,"y": 1180,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2920,"y": 1180,"theta": 0.0}},{ "task":"Vitesse normale","command":"speed#100","position":{"x": 2920,"y": 1180,"theta": 0.0}},{ "task":"Sortie aspiration","command":"goto-back#2600;1180","position":{"x": 2600,"y": 1180,"theta": 0.0}},{ "task":"Blocage assiette 3","command":"add-zone#start0_3","position":{"x": 2600,"y": 1180,"theta": 0.0}},{ "task":"Position vidage bouboule","command":"goto-astar#2600;1180","position":{"x": 2600,"y": 1180,"theta": 0.0}},{ "task":"Position vidage bouboule","command":"goto-astar#2800;1380","position":{"x": 2800,"y": 1380,"theta": 0.7853981633974483}},{ "task":"Position vidage bouboule","command":"goto-astar#2800;1770","position":{"x": 2800,"y": 1770,"theta": 1.5707963267948966}},{ "task":"Alignement vidage bouboule","command":"face#3000;1775","position":{"x": 2800,"y": 1770,"theta": 0.02499479361892016}},{ "task":"Position vomie","command":"go#100","position":{"x": 2899,"y": 1772,"theta": 0.02499479361892016}},{ "task":"Sortie vomie","command":"goto-back#2800;1775","position":{"x": 2800,"y": 1775,"theta": -0.03029375991877492}},{ "task":"Position aspiration","command":"goto-astar#2800;1770","position":{"x": 2800,"y": 1770,"theta": -1.5707963267948966}},{ "task":"Position aspiration","command":"goto-astar#2210;1180","position":{"x": 2210,"y": 1180,"theta": 3.9269908169872414}},{ "task":"Position aspiration","command":"goto-astar#450;1180","position":{"x": 450,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;1180","position":{"x": 450,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#350;1180","position":{"x": 350,"y": 1180,"theta": 3.141592653589793}},{ "task":"Reduction de vitesse","command":"speed#25","position":{"x": 350,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#320;1180","position":{"x": 320,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;1180","position":{"x": 320,"y": 1180,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 320,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#290;1180","position":{"x": 290,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;1180","position":{"x": 290,"y": 1180,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 290,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#260;1180","position":{"x": 260,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;1180","position":{"x": 260,"y": 1180,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 260,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#230;1180","position":{"x": 230,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;1180","position":{"x": 230,"y": 1180,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 230,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#200;1180","position":{"x": 200,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;1180","position":{"x": 200,"y": 1180,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 200,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#170;1180","position":{"x": 170,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;1180","position":{"x": 170,"y": 1180,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 170,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#140;1180","position":{"x": 140,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;1180","position":{"x": 140,"y": 1180,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 140,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#110;1180","position":{"x": 110,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;1180","position":{"x": 110,"y": 1180,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 110,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#80;1180","position":{"x": 80,"y": 1180,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;1180","position":{"x": 80,"y": 1180,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 80,"y": 1180,"theta": 3.141592653589793}},{ "task":"Vitesse normale","command":"speed#100","position":{"x": 80,"y": 1180,"theta": 3.141592653589793}},{ "task":"Sortie aspiration","command":"goto-back#450;1180","position":{"x": 450,"y": 1180,"theta": -3.141592653589793}},{ "task":"Position vidage bouboule","command":"goto-astar#450;1180","position":{"x": 450,"y": 1180,"theta": -3.141592653589793}},{ "task":"Position vidage bouboule","command":"goto-astar#650;1380","position":{"x": 650,"y": 1380,"theta": 0.7853981633974483}},{ "task":"Position vidage bouboule","command":"goto-astar#1730;1380","position":{"x": 1730,"y": 1380,"theta": 0.0}},{ "task":"Position vidage bouboule","command":"goto-astar#2120;1770","position":{"x": 2120,"y": 1770,"theta": 0.7853981633974483}},{ "task":"Position vidage bouboule","command":"goto-astar#2500;1770","position":{"x": 2500,"y": 1770,"theta": 0.0}},{ "task":"Alignement vidage bouboule","command":"face#0;1775","position":{"x": 2500,"y": 1770,"theta": 3.1395926562564536}},{ "task":"Position vidage bouboule","command":"goto#2800;1775","position":{"x": 2800,"y": 1775,"theta": 0.016665123713940747}},{ "task":"Alignement vidage bouboule","command":"face#3000;1775","position":{"x": 2800,"y": 1775,"theta": 0.0}},{ "task":"Position vomie","command":"go#100","position":{"x": 2900,"y": 1775,"theta": 0.0}}]
;var strategySmall3000 = [{ "task":"Position de départ","command":"start","position":{"x": 2900,"y": 225,"theta": 0.0}},{ "task":"On quitte le bord","command":"go#-250","position":{"x": 2650,"y": 225,"theta": 0.0}},{ "task":"Libération violet 2","command":"delete-zone#west_cake_purple_2","position":{"x": 2650,"y": 225,"theta": 0.0}},{ "task":"Libération jaune 2","command":"delete-zone#west_cake_yellow_2","position":{"x": 2650,"y": 225,"theta": 0.0}},{ "task":"Libération brun 2","command":"delete-zone#west_cake_brown_2","position":{"x": 2650,"y": 225,"theta": 0.0}},{ "task":"Libération brun 1","command":"delete-zone#west_cake_brown_1","position":{"x": 2650,"y": 225,"theta": 0.0}},{ "task":"Position aspiration","command":"goto-astar#2650;220","position":{"x": 2650,"y": 220,"theta": -1.5707963267948966}},{ "task":"Position aspiration","command":"goto-astar#2650;820","position":{"x": 2650,"y": 820,"theta": 1.5707963267948966}},{ "task":"Position aspiration","command":"face#3000;820","position":{"x": 2650,"y": 820,"theta": 0.0}},{ "task":"Reduction de vitesse","command":"speed#25","position":{"x": 2650,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2680;820","position":{"x": 2680,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;820","position":{"x": 2680,"y": 820,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2680,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2710;820","position":{"x": 2710,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;820","position":{"x": 2710,"y": 820,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2710,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2740;820","position":{"x": 2740,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;820","position":{"x": 2740,"y": 820,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2740,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2770;820","position":{"x": 2770,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;820","position":{"x": 2770,"y": 820,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2770,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2800;820","position":{"x": 2800,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;820","position":{"x": 2800,"y": 820,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2800,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2830;820","position":{"x": 2830,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;820","position":{"x": 2830,"y": 820,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2830,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2860;820","position":{"x": 2860,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;820","position":{"x": 2860,"y": 820,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2860,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2890;820","position":{"x": 2890,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;820","position":{"x": 2890,"y": 820,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2890,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2920;820","position":{"x": 2920,"y": 820,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;820","position":{"x": 2920,"y": 820,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2920,"y": 820,"theta": 0.0}},{ "task":"Vitesse normale","command":"speed#100","position":{"x": 2920,"y": 820,"theta": 0.0}},{ "task":"Sortie aspiration","command":"goto-back#2600;820","position":{"x": 2600,"y": 820,"theta": 0.0}},{ "task":"Position vidage bouboule","command":"goto-astar#2600;820","position":{"x": 2600,"y": 820,"theta": 0.0}},{ "task":"Position vidage bouboule","command":"goto-astar#2800;620","position":{"x": 2800,"y": 620,"theta": -0.7853981633974483}},{ "task":"Position vidage bouboule","command":"goto-astar#2800;220","position":{"x": 2800,"y": 220,"theta": -1.5707963267948966}},{ "task":"Alignement vidage bouboule","command":"face#3000;225","position":{"x": 2800,"y": 220,"theta": 0.02499479361892016}},{ "task":"Position vomie","command":"go#100","position":{"x": 2899,"y": 222,"theta": 0.02499479361892016}},{ "task":"Sortie vomie","command":"goto-back#2800;225","position":{"x": 2800,"y": 225,"theta": -0.03029375991877492}},{ "task":"Position aspiration","command":"goto-astar#2800;220","position":{"x": 2800,"y": 220,"theta": -1.5707963267948966}},{ "task":"Position aspiration","command":"goto-astar#2200;820","position":{"x": 2200,"y": 820,"theta": 2.356194490192345}},{ "task":"Position aspiration","command":"goto-astar#450;820","position":{"x": 450,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;820","position":{"x": 450,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#350;820","position":{"x": 350,"y": 820,"theta": 3.141592653589793}},{ "task":"Reduction de vitesse","command":"speed#25","position":{"x": 350,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#320;820","position":{"x": 320,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;820","position":{"x": 320,"y": 820,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 320,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#290;820","position":{"x": 290,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;820","position":{"x": 290,"y": 820,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 290,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#260;820","position":{"x": 260,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;820","position":{"x": 260,"y": 820,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 260,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#230;820","position":{"x": 230,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;820","position":{"x": 230,"y": 820,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 230,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#200;820","position":{"x": 200,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;820","position":{"x": 200,"y": 820,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 200,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#170;820","position":{"x": 170,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;820","position":{"x": 170,"y": 820,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 170,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#140;820","position":{"x": 140,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;820","position":{"x": 140,"y": 820,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 140,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#110;820","position":{"x": 110,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;820","position":{"x": 110,"y": 820,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 110,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"goto#80;820","position":{"x": 80,"y": 820,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#0;820","position":{"x": 80,"y": 820,"theta": 3.141592653589793}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 80,"y": 820,"theta": 3.141592653589793}},{ "task":"Vitesse normale","command":"speed#100","position":{"x": 80,"y": 820,"theta": 3.141592653589793}},{ "task":"Sortie aspiration","command":"goto-back#450;820","position":{"x": 450,"y": 820,"theta": -3.141592653589793}},{ "task":"Position vidage bouboule","command":"goto-astar#450;820","position":{"x": 450,"y": 820,"theta": -3.141592653589793}},{ "task":"Position vidage bouboule","command":"goto-astar#790;480","position":{"x": 790,"y": 480,"theta": -0.7853981633974483}},{ "task":"Position vidage bouboule","command":"goto-astar#2030;480","position":{"x": 2030,"y": 480,"theta": 0.0}},{ "task":"Position vidage bouboule","command":"goto-astar#2290;220","position":{"x": 2290,"y": 220,"theta": -0.7853981633974483}},{ "task":"Position vidage bouboule","command":"goto-astar#2500;220","position":{"x": 2500,"y": 220,"theta": 0.0}},{ "task":"Alignement vidage bouboule","command":"face#0;225","position":{"x": 2500,"y": 220,"theta": 3.1395926562564536}},{ "task":"Position vidage bouboule","command":"goto#2800;225","position":{"x": 2800,"y": 225,"theta": 0.016665123713940747}},{ "task":"Alignement vidage bouboule","command":"face#3000;225","position":{"x": 2800,"y": 225,"theta": 0.0}},{ "task":"Position vomie","command":"go#100","position":{"x": 2900,"y": 225,"theta": 0.0}}]
;
