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
          "y": 700
        },
        {
          "x": 3000,
          "y": 1300
        },
        {
          "x": 2700,
          "y": 1300
        },
        {
          "x": 2700,
          "y": 700
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
    {
      "desc": "depart bleu",
      "x1": 2550,
      "y1": 1550,
      "x2": 2550,
      "y2": 2000,
      "x3": 3000,
      "y3": 2000,
      "x4": 3000,
      "y4": 1550
    },
    {
      "desc": "depart vert",
      "x1": 2550,
      "y1": 0,
      "x2": 2550,
      "y2": 450,
      "x3": 3000,
      "y3": 450,
      "x4": 3000,
      "y4": 0
    }
  ]
};var strategyBig0 = [{ "task":"Position de départ","command":"start","position":{"x": 2720,"y": 1775,"theta": 3.141592653589793}},{ "task":"Step de départ bizarre","command":"go#1","position":{"x": 2719,"y": 1775,"theta": 3.141592653589793}},{ "task":"Init pince","command":"action#2","position":{"x": 2719,"y": 1775,"theta": 3.141592653589793}},{ "task":"Init ascenceur","command":"action#10","position":{"x": 2719,"y": 1775,"theta": 3.141592653589793}},{ "task":"Récupération violet 2","command":"goto#2630;1775","position":{"x": 2630,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise violet 2","command":"action#13","position":{"x": 2630,"y": 1775,"theta": 3.141592653589793}},{ "task":"Libération violet 2","command":"delete-zone#east_cake_purple_2","position":{"x": 2630,"y": 1775,"theta": 3.141592653589793}},{ "task":"Récupération jaune 2","command":"goto#2430;1775","position":{"x": 2430,"y": 1775,"theta": 3.141592653589793}},{ "task":"Récupération jaune 2","command":"face#0;1775","position":{"x": 2430,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#2","position":{"x": 2430,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"goto-back#2460;1775","position":{"x": 2460,"y": 1775,"theta": -3.141592653589793}},{ "task":"Prise jaune 2","command":"action#8","position":{"x": 2460,"y": 1775,"theta": -3.141592653589793}},{ "task":"Prise jaune 2","command":"action#9","position":{"x": 2460,"y": 1775,"theta": -3.141592653589793}},{ "task":"Prise jaune 2","command":"goto#2420;1775","position":{"x": 2420,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#0","position":{"x": 2420,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#0","position":{"x": 2420,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#0","position":{"x": 2420,"y": 1775,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#7","position":{"x": 2420,"y": 1775,"theta": 3.141592653589793}},{ "task":"Libération jaune 2","command":"delete-zone#east_cake_yellow_2","position":{"x": 2420,"y": 1775,"theta": 3.141592653589793}},{ "task":"Go assiette 4","command":"goto-astar#2420;1770","position":{"x": 2420,"y": 1770,"theta": -1.5707963267948966}},{ "task":"Go assiette 4","command":"goto-astar#2140;1490","position":{"x": 2140,"y": 1490,"theta": 3.9269908169872414}},{ "task":"Go assiette 4","command":"goto-astar#2140;610","position":{"x": 2140,"y": 610,"theta": -1.5707963267948966}},{ "task":"Go assiette 4","command":"goto-astar#1980;450","position":{"x": 1980,"y": 450,"theta": 3.9269908169872414}},{ "task":"Go assiette 4","command":"goto-astar#1750;450","position":{"x": 1750,"y": 450,"theta": 3.141592653589793}},{ "task":"Alignement assiette 4","command":"face#1750;0","position":{"x": 1750,"y": 450,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Position largage 1","command":"goto#1750;300","position":{"x": 1750,"y": 300,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Position largage 1","command":"face#1750;0","position":{"x": 1750,"y": 300,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Depilement 1","command":"action#15","position":{"x": 1750,"y": 300,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Position largage 2","command":"goto-back#1750;450","position":{"x": 1750,"y": 450,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Position largage 2","command":"face#1750;0","position":{"x": 1750,"y": 450,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Depilement 2","command":"action#15","position":{"x": 1750,"y": 450,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Position largage 3","command":"goto-back#1750;600","position":{"x": 1750,"y": 600,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Position largage 3","command":"face#1750;0","position":{"x": 1750,"y": 600,"theta": -1.5707963267948966}},{ "task":"Assiette 4 - Depilement 3","command":"action#15","position":{"x": 1750,"y": 600,"theta": -1.5707963267948966}},{ "task":"Libération assiette 4","command":"goto-back#1750;750","position":{"x": 1750,"y": 750,"theta": -1.5707963267948966}},{ "task":"Libération assiette 4","command":"goto#1600;750","position":{"x": 1600,"y": 750,"theta": 3.141592653589793}},{ "task":"Go assiette 3","command":"goto-astar#1600;750","position":{"x": 1600,"y": 750,"theta": 3.141592653589793}},{ "task":"Go assiette 3","command":"goto-astar#1380;970","position":{"x": 1380,"y": 970,"theta": 2.356194490192345}},{ "task":"Go assiette 3","command":"goto-astar#1380;1380","position":{"x": 1380,"y": 1380,"theta": 1.5707963267948966}},{ "task":"Go assiette 3","command":"goto-astar#1350;1410","position":{"x": 1350,"y": 1410,"theta": 2.356194490192345}},{ "task":"Go assiette 3","command":"goto-astar#1350;1420","position":{"x": 1350,"y": 1420,"theta": 1.5707963267948966}},{ "task":"Go assiette 3","command":"goto-astar#1220;1550","position":{"x": 1220,"y": 1550,"theta": 2.356194490192345}},{ "task":"Go assiette 3","command":"goto-astar#1100;1550","position":{"x": 1100,"y": 1550,"theta": 3.141592653589793}},{ "task":"Alignement assiette 3","command":"face#1100;2000","position":{"x": 1100,"y": 1550,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Position largage 1","command":"goto#1100;1700","position":{"x": 1100,"y": 1700,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Position largage 1","command":"face#1100;2000","position":{"x": 1100,"y": 1700,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Depilement 1","command":"action#15","position":{"x": 1100,"y": 1700,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Position largage 2","command":"goto-back#1100;1550","position":{"x": 1100,"y": 1550,"theta": -4.71238898038469}},{ "task":"Assiette 3 - Position largage 2","command":"face#1100;2000","position":{"x": 1100,"y": 1550,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Depilement 2","command":"action#15","position":{"x": 1100,"y": 1550,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Position largage 3","command":"goto-back#1100;1400","position":{"x": 1100,"y": 1400,"theta": -4.71238898038469}},{ "task":"Assiette 3 - Position largage 3","command":"face#1100;2000","position":{"x": 1100,"y": 1400,"theta": 1.5707963267948966}},{ "task":"Assiette 3 - Depilement 3","command":"action#15","position":{"x": 1100,"y": 1400,"theta": 1.5707963267948966}},{ "task":"Libération assiette 3","command":"goto-back#1100;1300","position":{"x": 1100,"y": 1300,"theta": -4.71238898038469}},{ "task":"Blocage assiette 3","command":"add-zone#start0_3","position":{"x": 1100,"y": 1300,"theta": -4.71238898038469}},{ "task":"Libération brun 1","command":"delete-zone#east_cake_brown_1","position":{"x": 1100,"y": 1300,"theta": -4.71238898038469}},{ "task":"Rangement pince","command":"action#7","position":{"x": 1100,"y": 1300,"theta": -4.71238898038469}},{ "task":"Rangement pince","command":"action#2","position":{"x": 1100,"y": 1300,"theta": -4.71238898038469}},{ "task":"Go attente assiette 1","command":"goto-astar#1100;1300","position":{"x": 1100,"y": 1300,"theta": -4.71238898038469}},{ "task":"Go attente assiette 1","command":"goto-astar#1170;1370","position":{"x": 1170,"y": 1370,"theta": 0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#1230;1370","position":{"x": 1230,"y": 1370,"theta": 0.0}},{ "task":"Go attente assiette 1","command":"goto-astar#1240;1380","position":{"x": 1240,"y": 1380,"theta": 0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#1400;1380","position":{"x": 1400,"y": 1380,"theta": 0.0}},{ "task":"Go attente assiette 1","command":"goto-astar#1420;1400","position":{"x": 1420,"y": 1400,"theta": 0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#1610;1400","position":{"x": 1610,"y": 1400,"theta": 0.0}},{ "task":"Go attente assiette 1","command":"goto-astar#1720;1510","position":{"x": 1720,"y": 1510,"theta": 0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#1750;1510","position":{"x": 1750,"y": 1510,"theta": 0.0}},{ "task":"Go attente assiette 1","command":"goto-astar#1760;1520","position":{"x": 1760,"y": 1520,"theta": 0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#1770;1520","position":{"x": 1770,"y": 1520,"theta": 0.0}},{ "task":"Go attente assiette 1","command":"goto-astar#1780;1530","position":{"x": 1780,"y": 1530,"theta": 0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#2270;1530","position":{"x": 2270,"y": 1530,"theta": 0.0}},{ "task":"Go attente assiette 1","command":"goto-astar#2300;1500","position":{"x": 2300,"y": 1500,"theta": -0.7853981633974483}},{ "task":"Wait 90s","command":"wait_chrono#0","position":{"x": 2300,"y": 1500,"theta": -0.7853981633974483}},{ "task":"Go assiette 1","command":"goto#2450;1500","position":{"x": 2450,"y": 1500,"theta": 0.0}}]
;var strategyBig3000 = [{ "task":"Position de départ","command":"start","position":{"x": 2720,"y": 225,"theta": 3.141592653589793}},{ "task":"Step de départ bizarre","command":"go#1","position":{"x": 2719,"y": 225,"theta": 3.141592653589793}},{ "task":"Init pince","command":"action#2","position":{"x": 2719,"y": 225,"theta": 3.141592653589793}},{ "task":"Init ascenceur","command":"action#10","position":{"x": 2719,"y": 225,"theta": 3.141592653589793}},{ "task":"Récupération violet 2","command":"goto#2630;225","position":{"x": 2630,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise violet 2","command":"action#13","position":{"x": 2630,"y": 225,"theta": 3.141592653589793}},{ "task":"Libération violet 2","command":"delete-zone#west_cake_purple_2","position":{"x": 2630,"y": 225,"theta": 3.141592653589793}},{ "task":"Récupération jaune 2","command":"goto#2430;225","position":{"x": 2430,"y": 225,"theta": 3.141592653589793}},{ "task":"Récupération jaune 2","command":"face#0;225","position":{"x": 2430,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#2","position":{"x": 2430,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"goto-back#2460;225","position":{"x": 2460,"y": 225,"theta": -3.141592653589793}},{ "task":"Prise jaune 2","command":"action#8","position":{"x": 2460,"y": 225,"theta": -3.141592653589793}},{ "task":"Prise jaune 2","command":"action#9","position":{"x": 2460,"y": 225,"theta": -3.141592653589793}},{ "task":"Prise jaune 2","command":"goto#2420;225","position":{"x": 2420,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#0","position":{"x": 2420,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#0","position":{"x": 2420,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#0","position":{"x": 2420,"y": 225,"theta": 3.141592653589793}},{ "task":"Prise jaune 2","command":"action#7","position":{"x": 2420,"y": 225,"theta": 3.141592653589793}},{ "task":"Libération jaune 2","command":"delete-zone#west_cake_yellow_2","position":{"x": 2420,"y": 225,"theta": 3.141592653589793}},{ "task":"Go assiette 4","command":"goto-astar#2420;220","position":{"x": 2420,"y": 220,"theta": -1.5707963267948966}},{ "task":"Go assiette 4","command":"goto-astar#2140;500","position":{"x": 2140,"y": 500,"theta": 2.356194490192345}},{ "task":"Go assiette 4","command":"goto-astar#2140;1380","position":{"x": 2140,"y": 1380,"theta": 1.5707963267948966}},{ "task":"Go assiette 4","command":"goto-astar#2110;1410","position":{"x": 2110,"y": 1410,"theta": 2.356194490192345}},{ "task":"Go assiette 4","command":"goto-astar#2110;1420","position":{"x": 2110,"y": 1420,"theta": 1.5707963267948966}},{ "task":"Go assiette 4","command":"goto-astar#1980;1550","position":{"x": 1980,"y": 1550,"theta": 2.356194490192345}},{ "task":"Go assiette 4","command":"goto-astar#1750;1550","position":{"x": 1750,"y": 1550,"theta": 3.141592653589793}},{ "task":"Alignement assiette 4","command":"face#1750;2000","position":{"x": 1750,"y": 1550,"theta": 1.5707963267948966}},{ "task":"Assiette 4 - Position largage 1","command":"goto#1750;1700","position":{"x": 1750,"y": 1700,"theta": 1.5707963267948966}},{ "task":"Assiette 4 - Position largage 1","command":"face#1750;2000","position":{"x": 1750,"y": 1700,"theta": 1.5707963267948966}},{ "task":"Assiette 4 - Depilement 1","command":"action#15","position":{"x": 1750,"y": 1700,"theta": 1.5707963267948966}},{ "task":"Assiette 4 - Position largage 2","command":"goto-back#1750;1550","position":{"x": 1750,"y": 1550,"theta": -4.71238898038469}},{ "task":"Assiette 4 - Position largage 2","command":"face#1750;2000","position":{"x": 1750,"y": 1550,"theta": 1.5707963267948966}},{ "task":"Assiette 4 - Depilement 2","command":"action#15","position":{"x": 1750,"y": 1550,"theta": 1.5707963267948966}},{ "task":"Assiette 4 - Position largage 3","command":"goto-back#1750;1400","position":{"x": 1750,"y": 1400,"theta": -4.71238898038469}},{ "task":"Assiette 4 - Position largage 3","command":"face#1750;2000","position":{"x": 1750,"y": 1400,"theta": 1.5707963267948966}},{ "task":"Assiette 4 - Depilement 3","command":"action#15","position":{"x": 1750,"y": 1400,"theta": 1.5707963267948966}},{ "task":"Libération assiette 4","command":"goto-back#1750;1250","position":{"x": 1750,"y": 1250,"theta": -4.71238898038469}},{ "task":"Libération assiette 4","command":"goto#1600;1250","position":{"x": 1600,"y": 1250,"theta": 3.141592653589793}},{ "task":"Go assiette 3","command":"goto-astar#1600;1250","position":{"x": 1600,"y": 1250,"theta": 3.141592653589793}},{ "task":"Go assiette 3","command":"goto-astar#1380;1030","position":{"x": 1380,"y": 1030,"theta": 3.9269908169872414}},{ "task":"Go assiette 3","command":"goto-astar#1380;620","position":{"x": 1380,"y": 620,"theta": -1.5707963267948966}},{ "task":"Go assiette 3","command":"goto-astar#1360;600","position":{"x": 1360,"y": 600,"theta": 3.9269908169872414}},{ "task":"Go assiette 3","command":"goto-astar#1360;580","position":{"x": 1360,"y": 580,"theta": -1.5707963267948966}},{ "task":"Go assiette 3","command":"goto-astar#1260;480","position":{"x": 1260,"y": 480,"theta": 3.9269908169872414}},{ "task":"Go assiette 3","command":"goto-astar#1240;480","position":{"x": 1240,"y": 480,"theta": 3.141592653589793}},{ "task":"Go assiette 3","command":"goto-astar#1210;450","position":{"x": 1210,"y": 450,"theta": 3.9269908169872414}},{ "task":"Go assiette 3","command":"goto-astar#1100;450","position":{"x": 1100,"y": 450,"theta": 3.141592653589793}},{ "task":"Alignement assiette 3","command":"face#1100;0","position":{"x": 1100,"y": 450,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 1","command":"goto#1100;300","position":{"x": 1100,"y": 300,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 1","command":"face#1100;0","position":{"x": 1100,"y": 300,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Depilement 1","command":"action#15","position":{"x": 1100,"y": 300,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 2","command":"goto-back#1100;450","position":{"x": 1100,"y": 450,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 2","command":"face#1100;0","position":{"x": 1100,"y": 450,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Depilement 2","command":"action#15","position":{"x": 1100,"y": 450,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 3","command":"goto-back#1100;600","position":{"x": 1100,"y": 600,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Position largage 3","command":"face#1100;0","position":{"x": 1100,"y": 600,"theta": -1.5707963267948966}},{ "task":"Assiette 3 - Depilement 3","command":"action#15","position":{"x": 1100,"y": 600,"theta": -1.5707963267948966}},{ "task":"Libération assiette 3","command":"goto-back#1100;700","position":{"x": 1100,"y": 700,"theta": -1.5707963267948966}},{ "task":"Blocage assiette 3","command":"add-zone#start3000_3","position":{"x": 1100,"y": 700,"theta": -1.5707963267948966}},{ "task":"Libération brun 1","command":"delete-zone#west_cake_brown_1","position":{"x": 1100,"y": 700,"theta": -1.5707963267948966}},{ "task":"Rangement pince","command":"action#7","position":{"x": 1100,"y": 700,"theta": -1.5707963267948966}},{ "task":"Rangement pince","command":"action#2","position":{"x": 1100,"y": 700,"theta": -1.5707963267948966}},{ "task":"Go attente assiette 1","command":"goto-astar#1100;700","position":{"x": 1100,"y": 700,"theta": -1.5707963267948966}},{ "task":"Go attente assiette 1","command":"goto-astar#1180;620","position":{"x": 1180,"y": 620,"theta": -0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#1430;620","position":{"x": 1430,"y": 620,"theta": 0.0}},{ "task":"Go attente assiette 1","command":"goto-astar#1550;500","position":{"x": 1550,"y": 500,"theta": -0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#1710;500","position":{"x": 1710,"y": 500,"theta": 0.0}},{ "task":"Go attente assiette 1","command":"goto-astar#1730;480","position":{"x": 1730,"y": 480,"theta": -0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#1750;480","position":{"x": 1750,"y": 480,"theta": 0.0}},{ "task":"Go attente assiette 1","command":"goto-astar#1760;470","position":{"x": 1760,"y": 470,"theta": -0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#1770;470","position":{"x": 1770,"y": 470,"theta": 0.0}},{ "task":"Go attente assiette 1","command":"goto-astar#1780;460","position":{"x": 1780,"y": 460,"theta": -0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#2270;460","position":{"x": 2270,"y": 460,"theta": 0.0}},{ "task":"Go attente assiette 1","command":"goto-astar#2300;490","position":{"x": 2300,"y": 490,"theta": 0.7853981633974483}},{ "task":"Go attente assiette 1","command":"goto-astar#2300;500","position":{"x": 2300,"y": 500,"theta": 1.5707963267948966}},{ "task":"Wait 90s","command":"wait_chrono#0","position":{"x": 2300,"y": 500,"theta": 1.5707963267948966}},{ "task":"Go assiette 1","command":"goto#2450;500","position":{"x": 2450,"y": 500,"theta": 0.0}}]
;var strategySmall0 = [{ "task":"Position de départ","command":"start","position":{"x": 2920,"y": 1775,"theta": 0.0}},{ "task":"On recule pour souffler","command":"go#-20","position":{"x": 2900,"y": 1775,"theta": 0.0}},{ "task":"Alignement tir","command":"face#3000;1775","position":{"x": 2900,"y": 1775,"theta": 0.0}},{ "task":"Souffler bouboule","command":"action#39","position":{"x": 2900,"y": 1775,"theta": 0.0}},{ "task":"On recule","command":"goto-back#2650;1760","position":{"x": 2650,"y": 1760,"theta": 0.05992815512120808}},{ "task":"Libération violet 2","command":"delete-zone#east_cake_purple_2","position":{"x": 2650,"y": 1760,"theta": 0.05992815512120808}},{ "task":"Libération jaune 2","command":"delete-zone#east_cake_yellow_2","position":{"x": 2650,"y": 1760,"theta": 0.05992815512120808}},{ "task":"Libération brun 2","command":"delete-zone#east_cake_brown_2","position":{"x": 2650,"y": 1760,"theta": 0.05992815512120808}},{ "task":"Libération brun 1","command":"delete-zone#east_cake_brown_1","position":{"x": 2650,"y": 1760,"theta": 0.05992815512120808}},{ "task":"Go assiette 3","command":"goto#2200;1760","position":{"x": 2200,"y": 1760,"theta": 3.141592653589793}},{ "task":"Go assiette 3","command":"goto#1800;1760","position":{"x": 1800,"y": 1760,"theta": 3.141592653589793}},{ "task":"Go assiette 3","command":"goto#1400;1760","position":{"x": 1400,"y": 1760,"theta": 3.141592653589793}},{ "task":"Go assiette 3","command":"goto#1250;1760","position":{"x": 1250,"y": 1760,"theta": 3.141592653589793}},{ "task":"Go assiette 3","command":"goto-back#1500;1760","position":{"x": 1500,"y": 1760,"theta": -3.141592653589793}},{ "task":"Position aspiration","command":"goto-astar#1500;1760","position":{"x": 1500,"y": 1760,"theta": -3.141592653589793}},{ "task":"Position aspiration","command":"goto-astar#2100;1160","position":{"x": 2100,"y": 1160,"theta": -0.7853981633974483}},{ "task":"Position aspiration","command":"goto-astar#2550;1163","position":{"x": 2550,"y": 1163,"theta": 0.006666567903868229}},{ "task":"Position aspiration","command":"face#3000;1163","position":{"x": 2550,"y": 1163,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2650;1163","position":{"x": 2650,"y": 1163,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1163","position":{"x": 2650,"y": 1163,"theta": 0.0}},{ "task":"Aspiration cherry bouboule sud","command":"action#36","position":{"x": 2650,"y": 1163,"theta": 0.0}},{ "task":"Aspiration cherry bouboule sud","command":"action#28","position":{"x": 2650,"y": 1163,"theta": 0.0}},{ "task":"Reduction de vitesse","command":"speed#25","position":{"x": 2650,"y": 1163,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2650;1163","position":{"x": 2650,"y": 1163,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#3000;1163","position":{"x": 2650,"y": 1163,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2650,"y": 1163,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2680;1163","position":{"x": 2680,"y": 1163,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1163","position":{"x": 2680,"y": 1163,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2680,"y": 1163,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2710;1163","position":{"x": 2710,"y": 1163,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1163","position":{"x": 2710,"y": 1163,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2710,"y": 1163,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2740;1163","position":{"x": 2740,"y": 1163,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1163","position":{"x": 2740,"y": 1163,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2740,"y": 1163,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2770;1163","position":{"x": 2770,"y": 1163,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1163","position":{"x": 2770,"y": 1163,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2770,"y": 1163,"theta": 0.0}},{ "task":"Stockage bouboule sud","command":"action#41","position":{"x": 2770,"y": 1163,"theta": 0.0}},{ "task":"Aspiration cherry bouboule sud","command":"action#36","position":{"x": 2770,"y": 1163,"theta": 0.0}},{ "task":"Aspiration cherry bouboule sud","command":"action#28","position":{"x": 2770,"y": 1163,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2800;1163","position":{"x": 2800,"y": 1163,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1163","position":{"x": 2800,"y": 1163,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2800,"y": 1163,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2830;1163","position":{"x": 2830,"y": 1163,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1163","position":{"x": 2830,"y": 1163,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2830,"y": 1163,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2860;1163","position":{"x": 2860,"y": 1163,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;1163","position":{"x": 2860,"y": 1163,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2860,"y": 1163,"theta": 0.0}},{ "task":"Fin aspiration sud","command":"action#30","position":{"x": 2860,"y": 1163,"theta": 0.0}},{ "task":"Fin aspiration sud","command":"action#34","position":{"x": 2860,"y": 1163,"theta": 0.0}},{ "task":"Vitesse normale","command":"speed#100","position":{"x": 2860,"y": 1163,"theta": 0.0}},{ "task":"Sortie aspiration","command":"goto-back#2600;1163","position":{"x": 2600,"y": 1163,"theta": 0.0}},{ "task":"Position vidage bouboule","command":"goto-astar#2600;1160","position":{"x": 2600,"y": 1160,"theta": -1.5707963267948966}},{ "task":"Position vidage bouboule","command":"goto-astar#2550;1210","position":{"x": 2550,"y": 1210,"theta": 2.356194490192345}},{ "task":"Position vidage bouboule","command":"goto-astar#2550;1730","position":{"x": 2550,"y": 1730,"theta": 1.5707963267948966}},{ "task":"Alignement vidage bouboule","command":"face#3000;1730","position":{"x": 2550,"y": 1730,"theta": 0.0}},{ "task":"Alignement vidage bouboule","command":"goto#2830;1730","position":{"x": 2830,"y": 1730,"theta": 0.0}},{ "task":"Alignement vidage bouboule","command":"face#3000;1730","position":{"x": 2830,"y": 1730,"theta": 0.0}},{ "task":"Vomie sud 1","command":"action#40","position":{"x": 2830,"y": 1730,"theta": 0.0}},{ "task":"Vomie sud reserve","command":"action#42","position":{"x": 2830,"y": 1730,"theta": 0.0}},{ "task":"Attente second batch vomie","command":"wait#0","position":{"x": 2830,"y": 1730,"theta": 0.0}},{ "task":"Vomie sud 2","command":"action#40","position":{"x": 2830,"y": 1730,"theta": 0.0}},{ "task":"Alignement vidage bouboule","command":"goto-back#2750;1730","position":{"x": 2750,"y": 1730,"theta": 0.0}},{ "task":"Alignement vidage bouboule","command":"face#0;0","position":{"x": 2750,"y": 1730,"theta": 3.703128338960494}}]
;var strategySmall3000 = [{ "task":"Position de départ","command":"start","position":{"x": 2920,"y": 225,"theta": 0.0}},{ "task":"On recule pour souffler","command":"go#-20","position":{"x": 2900,"y": 225,"theta": 0.0}},{ "task":"Alignement tir","command":"face#3000;225","position":{"x": 2900,"y": 225,"theta": 0.0}},{ "task":"Souffler bouboule","command":"action#39","position":{"x": 2900,"y": 225,"theta": 0.0}},{ "task":"On recule","command":"goto-back#2650;240","position":{"x": 2650,"y": 240,"theta": -0.05992815512120808}},{ "task":"Libération violet 2","command":"delete-zone#west_cake_purple_2","position":{"x": 2650,"y": 240,"theta": -0.05992815512120808}},{ "task":"Libération jaune 2","command":"delete-zone#west_cake_yellow_2","position":{"x": 2650,"y": 240,"theta": -0.05992815512120808}},{ "task":"Libération brun 2","command":"delete-zone#west_cake_brown_2","position":{"x": 2650,"y": 240,"theta": -0.05992815512120808}},{ "task":"Libération brun 1","command":"delete-zone#west_cake_brown_1","position":{"x": 2650,"y": 240,"theta": -0.05992815512120808}},{ "task":"Go assiette 3","command":"goto#2200;240","position":{"x": 2200,"y": 240,"theta": 3.141592653589793}},{ "task":"Go assiette 3","command":"goto#1800;240","position":{"x": 1800,"y": 240,"theta": 3.141592653589793}},{ "task":"Go assiette 3","command":"goto#1400;240","position":{"x": 1400,"y": 240,"theta": 3.141592653589793}},{ "task":"Go assiette 3","command":"goto#1250;240","position":{"x": 1250,"y": 240,"theta": 3.141592653589793}},{ "task":"Go assiette 3","command":"goto-back#1500;240","position":{"x": 1500,"y": 240,"theta": -3.141592653589793}},{ "task":"Position aspiration","command":"goto-astar#1500;240","position":{"x": 1500,"y": 240,"theta": -3.141592653589793}},{ "task":"Position aspiration","command":"goto-astar#2120;860","position":{"x": 2120,"y": 860,"theta": 0.7853981633974483}},{ "task":"Position aspiration","command":"goto-astar#2550;865","position":{"x": 2550,"y": 865,"theta": 0.01162738295638387}},{ "task":"Position aspiration","command":"face#3000;865","position":{"x": 2550,"y": 865,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2650;865","position":{"x": 2650,"y": 865,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;865","position":{"x": 2650,"y": 865,"theta": 0.0}},{ "task":"Aspiration cherry bouboule sud","command":"action#36","position":{"x": 2650,"y": 865,"theta": 0.0}},{ "task":"Aspiration cherry bouboule sud","command":"action#29","position":{"x": 2650,"y": 865,"theta": 0.0}},{ "task":"Reduction de vitesse","command":"speed#25","position":{"x": 2650,"y": 865,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2650;865","position":{"x": 2650,"y": 865,"theta": 3.141592653589793}},{ "task":"Position aspiration","command":"face#3000;865","position":{"x": 2650,"y": 865,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2650,"y": 865,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2680;865","position":{"x": 2680,"y": 865,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;865","position":{"x": 2680,"y": 865,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2680,"y": 865,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2710;865","position":{"x": 2710,"y": 865,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;865","position":{"x": 2710,"y": 865,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2710,"y": 865,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2740;865","position":{"x": 2740,"y": 865,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;865","position":{"x": 2740,"y": 865,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2740,"y": 865,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2770;865","position":{"x": 2770,"y": 865,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;865","position":{"x": 2770,"y": 865,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2770,"y": 865,"theta": 0.0}},{ "task":"Stockage bouboule sud","command":"action#41","position":{"x": 2770,"y": 865,"theta": 0.0}},{ "task":"Aspiration cherry bouboule sud","command":"action#36","position":{"x": 2770,"y": 865,"theta": 0.0}},{ "task":"Aspiration cherry bouboule sud","command":"action#29","position":{"x": 2770,"y": 865,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2800;865","position":{"x": 2800,"y": 865,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;865","position":{"x": 2800,"y": 865,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2800,"y": 865,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2830;865","position":{"x": 2830,"y": 865,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;865","position":{"x": 2830,"y": 865,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2830,"y": 865,"theta": 0.0}},{ "task":"Position aspiration","command":"goto#2860;865","position":{"x": 2860,"y": 865,"theta": 0.0}},{ "task":"Position aspiration","command":"face#3000;865","position":{"x": 2860,"y": 865,"theta": 0.0}},{ "task":"On laisse le temps","command":"wait#0","position":{"x": 2860,"y": 865,"theta": 0.0}},{ "task":"Fin aspiration sud","command":"action#30","position":{"x": 2860,"y": 865,"theta": 0.0}},{ "task":"Fin aspiration sud","command":"action#34","position":{"x": 2860,"y": 865,"theta": 0.0}},{ "task":"Vitesse normale","command":"speed#100","position":{"x": 2860,"y": 865,"theta": 0.0}},{ "task":"Sortie aspiration","command":"goto-back#2600;865","position":{"x": 2600,"y": 865,"theta": 0.0}},{ "task":"Position vidage bouboule","command":"goto-astar#2600;860","position":{"x": 2600,"y": 860,"theta": -1.5707963267948966}},{ "task":"Position vidage bouboule","command":"goto-astar#2550;810","position":{"x": 2550,"y": 810,"theta": 3.9269908169872414}},{ "task":"Position vidage bouboule","command":"goto-astar#2550;270","position":{"x": 2550,"y": 270,"theta": -1.5707963267948966}},{ "task":"Alignement vidage bouboule","command":"face#3000;270","position":{"x": 2550,"y": 270,"theta": 0.0}},{ "task":"Alignement vidage bouboule","command":"goto#2830;270","position":{"x": 2830,"y": 270,"theta": 0.0}},{ "task":"Alignement vidage bouboule","command":"face#3000;270","position":{"x": 2830,"y": 270,"theta": 0.0}},{ "task":"Vomie sud 1","command":"action#40","position":{"x": 2830,"y": 270,"theta": 0.0}},{ "task":"Vomie sud reserve","command":"action#42","position":{"x": 2830,"y": 270,"theta": 0.0}},{ "task":"Attente second batch vomie","command":"wait#0","position":{"x": 2830,"y": 270,"theta": 0.0}},{ "task":"Vomie sud 2","command":"action#40","position":{"x": 2830,"y": 270,"theta": 0.0}},{ "task":"Alignement vidage bouboule","command":"goto-back#2750;270","position":{"x": 2750,"y": 270,"theta": 0.0}},{ "task":"Alignement vidage bouboule","command":"face#0;2000","position":{"x": 2750,"y": 270,"theta": 2.5800569682190924}}]
;
