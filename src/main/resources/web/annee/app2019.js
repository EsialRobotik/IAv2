$( function() {
    const actionToolsTemplate = $("#templates .action-item__hidden-actions-container");
    let isAutoPlaying = false;
    
    const setIsAutoPlaying = function(isPlaying, source) {
        isPlaying = !!isPlaying
        isAutoPlaying = isPlaying;
        
        if (isPlaying) {
        } else {
            
        }
        
        $(".actions-controls .ui-icon-arrowrefresh-1-w").parent().prop('disabled', isPlaying);
        $(".actions-controls .ui-icon-play").parent().prop('disabled', isPlaying ? 'disabled' : false);
        
        $(".actions-controls .ui-icon-seek-start").parent().prop('disabled', isPlaying ? 'disabled' : false);
        $(".actions-controls .ui-icon-seek-end").parent().prop('disabled', isPlaying ? 'disabled' : false);
    }
    
    const doajax = function(method, url, data) {
        return new Promise((resolve, reject) => {
            $.ajax({
                type: method,
                url: url,
                data: JSON.stringify(data),
                contentType: "application/json",
            })
            .then(resolve)
            .fail(reject);
        });
    }
    
    const afficherErreur = function(message) {
        $.toast({
            heading: 'Erreur',
            text: message,
            showHideTransition: 'fade',
            icon: 'error',
            position: 'top-left',
        });
    }
    
    const afficherMessage = function(message) {
        $.toast({
            heading: 'Info',
            text: message,
            showHideTransition: 'fade',
            icon: 'info',
            position: 'top-left',
        });
    }
    
    const handleAjaxError = function(response) {
        if (response.responseJSON) {
            afficherErreur(response.responseJSON.error || JSON.stringify(response.responseJSON));
        } else if (response.responseText) {
            afficherErreur(JSON.stringify(response.responseText));
        } else {
            afficherErreur(JSON.stringify(response));
        }
        console.error(response);
    }
    
    const getFirstItem = function() {
        return $("#listeActions").find(".actions-item.active").get(0);
    }
    
    const getCurrentPlayingItem = function() {
        return $("#listeActions").find(".actions-item.playing").get(0);
    };
    
    const scrollToElement = function(elt) {
        $([document.documentElement, document.body]).stop().animate({
            scrollTop: elt.offset().top - elt.height()
        }, 300);   
    }
    
    const playItemAsync = function(e, autoScroll) {
        $("#listeActions .actions-item").removeClass("playing");
        const elt = $(e);
        elt.addClass("playing");
        
        if (autoScroll) {
            scrollToElement(elt);
        }
        
        return doajax('post', '/api/play', {
            'actionPools': [
                {
                    'actions': actionPoolToModel(elt)
                }
            ]
        });
    };
    
    const playItem = function(e) {
        playItemAsync(e).catch(handleAjaxError);
    }
    
    const playFirstItemAsync = function() {
        let item = getFirstItem();
        if (item) {
            return playItemAsync(item, true);
        }
    }
    
    const playCurrentItemAsync = function() {
        let item = getCurrentPlayingItem();
        if (!item) {
            item = getFirstItem();
        }
        
        if (item) {
            return playItemAsync(item, true);
        }
    }
    
    const playPreviousItemAsync = function() {
        let item = getCurrentPlayingItem();
        if (!item) {
            item = getFirstItem();
        } else {
            item = $(item).prev('.actions-item.active').get(0);
        }
        
        if (item) {
            return playItemAsync(item, true);
        }
    };
    
    const playPreviousItem = function() {
        playPreviousItemAsync().catch(handleAjaxError);
    }
    
    const playNextItemAsync = function () {
        let item = getCurrentPlayingItem();
        if (!item) {
            item = getFirstItem();
        } else {
            item = $(item).next('.actions-item.active').get(0);
        }
        
        if (item) {
            return playItemAsync(item, true);
        }
    };
    
    const playNextItem = function() {
        playNextItemAsync().catch(handleAjaxError);
    }
    
    const playNextUntilEndOrStopAsync = function() {
        if (!isAutoPlaying) {
            return;
        }
        const promise = playNextItemAsync();
        if (!promise) {
            setIsAutoPlaying(false);
            return;
        }
        
        promise.then(() => {
            playNextUntilEndOrStopAsync();
        }).catch(handleAjaxError);
    }
    
    const playAllFromCurrent = function() {
        setIsAutoPlaying(true);
        
        const promise = playCurrentItemAsync();
        if (promise) {
            promise.then(() => {
                playNextUntilEndOrStopAsync();
            }).catch(handleAjaxError).then(() => setIsAutoPlaying(false));
        } else {
            setIsAutoPlaying(false);
        }
    }
    
    const playFirstItem = function() {
        setIsAutoPlaying(false);
        playFirstItemAsync().catch(handleAjaxError);
    };
    
    const stopPlaying = function() {
        if (!isAutoPlaying) {
            $("#listeActions .actions-item").removeClass("playing");
        }
        setIsAutoPlaying(false);
    }
    
    const deleteAllItems = function() {
        $("#listeActions .actions-item").each((index, item) => {
            item.remove();
        });
    }
    
    const newActionPool = function(title, setActive) {
        const elt = $('<li class="ui-state-default actions-item"><div class="actions-item__header"><span class="ui-icon ui-icon-arrowthick-2-n-s"></span>' + title + '</div></li>');
        addToolsElements(elt);
        if (setActive) {
            elt.addClass("active");
        }
        return elt;
    }
    
    const actionPoolToModel = function(actionPool) {
        const actions = [];
        actionPool.find('.action-subitem').each((index, action) => {
            const a = actionToModel($(action));
            
            if (a !== undefined) {
                actions.push(a);
            }
        });;
        
        return actions;
    }
    
    const actionToModel = function(action) {
        if (action.hasClass('action-subitem__ax12')) {
            return {
              "actionId": "actionPosition",
              "ax12Id": parseInt(action.find(".action-item__ax12-id").val()),
              "rawAngle": parseInt(action.find('input').data('raw-angle')),
              "readOnlyAngleDegrees": parseFloat(action.find('input').val())
            };
        }
        
        if (action.hasClass('action-subitem__pump')) {
            return {
                "actionId": "actionPump",
                "switchState": action.find(".action-item__pump-power").val() === '1',
                "pumpId": action.find(".action-subitem__pump-id").val() === 'left' ? 0 : 1
            };
        }
        
        if (action.hasClass('action-subitem__delay')) {
            return {
                "actionId": "actionWaiting",
                "waitingTimeMs": parseInt(action.find(".action-subitem__delay-value").val())
            }
        }
        
        if (action.hasClass('action-subitem__behaviour')) {
            return {
                actionId: "actionBehaviour",
                ax12Id: parseInt(action.find(".action-subitem__behaviour-id").val()),
            	speed: parseInt(action.find(".action-subitem__behaviour-speed").val()),
            	acceleration: parseInt(action.find(".action-subitem__behaviour-acceleration").val()),
            	compliance: parseInt(action.find(".action-subitem__behaviour-compliance").val())
            }
        }
        
        if (action.hasClass('action-subitem__disable-torque')) {
            return {
                actionId: "actionDisableTorque",
                ax12Id: parseInt(action.find(".action-subitem__disable-torque-id").val()),
            }
        }
        // default : undefined
    }
    
    const addPoolItem = function(item, markPlaying, scrollToIt) {
        $("#listeActions").append(item);
        if (markPlaying) {
            $("#listeActions .actions-item").removeClass('playing');;
            item.addClass('playing');
        }
        if (scrollToIt) {
            scrollToElement(item);
        }
    }
    
    const getPoolItemCount = function () {
        return $("#listeActions").find('.actions-item').length;
    }
    
    const cloneTemplate = function(template, params) {
        let elt = null;
        if (!params) {
        	params = {};
        }
        
        switch (template) {
            case "ax12":
                elt = $("#templates .action-subitem__ax12").clone();
                elt.find(".action-item__ax12-id").val(params.ax12Id);
                elt.find(".action-item__ax12-angle").val(params.readableAngle);
                elt.find(".action-item__ax12-angle").data('raw-angle', params.rawAngle);
                
                elt.find(".ui-icon-folder-open").parent().click(() => {
                    doajax('post', '/api/read', {
                        'property': 'ax12Angle',
                        'id': elt.find('select').val(),
                    }).then((result) => {
                        elt.find('input').val(result.readablePosition);
                        elt.find('input').data('raw-angle', result.rawPosition);
                    }).catch(handleAjaxError);
                });
                break;
            case "pump":
                elt = $("#templates .action-subitem__pump").clone();
                elt.find(".action-subitem__pump-id").val(params.pumpId);
                elt.find(".action-item__pump-power").val(params.power);
                break;
            case "waiting":
                elt = $("#templates .action-subitem__delay").clone();
                elt.find("action-subitem__delay-value").val(params.delay);
                break;
            case "behaviour":
            	elt = $("#templates .action-subitem__behaviour").clone();
            	elt.find(".action-subitem__behaviour-id").val(params.ax12Id);
            	elt.find(".action-subitem__behaviour-speed").val(params.speed);
            	elt.find(".action-subitem__behaviour-acceleration").val(params.acceleration);
            	elt.find(".action-subitem__behaviour-compliance").val(params.compliance);
            	break;
            case "disableTorque":
            	elt = $("#templates .action-subitem__disable-torque").clone();
            	elt.find(".action-subitem__disable-torque-id").val(params.ax12Id);
            	break;
            default:
                elt = $('<div class="action-subitem">');
                break;
        }
        
        elt.find(".ui-icon-trash").parent().click(() => {
            elt.remove();
        });
        
        return elt;
    }
    
    const addToolsElements = function (element) {
        const toolsElt = actionToolsTemplate.clone();
        toolsElt.find(".ui-icon-trash").parent().click(function() {
            element.remove();
        });
        
        toolsElt.find(".ui-icon-play").parent().click(function() {
            playItem(element);
        });
        
        toolsElt.find(".ui-icon-seek-next").parent().click(function() {
            playItemAsync(element).then(playAllFromCurrent).catch(handleAjaxError);
        });
        
        toolsElt.find(".ui-icon-power").parent().click(function() {
            $(element).toggleClass("active");
        });
        
        toolsElt.find(".ui-icon-copy").parent().click(function() {
            const clonedElt = $(element).clone();
            clonedElt.find(".action-item__hidden-actions-container").remove();
            clonedElt.removeClass("playing");
            addToolsElements(clonedElt);
            clonedElt.insertAfter($(element));
        });
        
        $(element).append(toolsElt);
    }
    
    const loadFromJson = function(o) {
        if (!Array.isArray(o.actionPools)) {
            afficherErreur("Pas de actionPools");
            return;
        }
        
        const poolsElts = [];
        o.actionPools.forEach((e) => {
            
            if (!e.actions || !Array.isArray(e.actions)) {
                return;
            }
            
            let poolNotEmpty = false;
            const poolElt = newActionPool("Pool #" + (poolsElts.length+1), true);
            
            e.actions.forEach((action) => {
                let actionSubitem = null;
                switch (action.actionId) {
                    case "actionPosition":
                        actionSubitem = cloneTemplate("ax12", {
                            ax12Id: action.ax12Id,
                            readableAngle: action.readOnlyAngleDegrees,
                            rawAngle: action.rawAngle
                        });
                    break;
                    case "actionPump":
                        actionSubitem = cloneTemplate("pump", {
                            pumpId: action.pumpId == 0 ? 'left' : 'right',
                            power: action.switchState ? '1' : '0'
                        });
                    break;
                    case "actionWaiting":
                        actionSubitem = cloneTemplate("waiting", {
                        	delay: action.waitingTimeMs
                        });
                    break;
                    case "actionBehaviour":
                    	actionSubitem = cloneTemplate("behaviour", {
                        	ax12Id: action.ax12Id,
                        	speed: action.speed,
                        	acceleration: action.acceleration,
                        	compliance: action.compliance
                    	});
                    break;
                    case "actionDisableTorque":
	                	actionSubitem = cloneTemplate("disableTorque", {
	                    	ax12Id: action.ax12Id
	                	});
                	break;
                }
                
                if (actionSubitem) {
                    poolElt.append(actionSubitem);
                    poolNotEmpty = true;
                }
            });
            
            
            if (poolNotEmpty) {
                poolsElts.push(poolElt);
            }
            
        });
        
        if (poolsElts.length > 0) {
            deleteAllItems();   
            poolsElts.forEach((pool) => {
                addPoolItem(pool);
            });
        }
    }
    
    const loadFromRawJson = function(rawJson) {
        let o = null;
        try {
            o = JSON.parse(rawJson);   
        } catch (e) {
            afficherErreur("Json non valide : " + e);
            return;
        }
        loadFromJson(o);
    }
    
    const encodeActionPoolsToModel = function() {
        const model = {
            'actionPools': []
        };
        
        $("#listeActions").find(".actions-item").each((index, pool) => {
            const actions = actionPoolToModel($(pool));
            
            if (actions.length > 0) {
                model.actionPools.push({
                    'actions': actions
                });
            }
        });
        
        return model;
    }
    
    const downloadActionPools = function() {
        const filename = $(".actions-records__json .actions-records__json_filename").val() || 'default.json';
        const dataUri = 'data:text/json;charset=utf-8,'+ encodeURIComponent(JSON.stringify(encodeActionPoolsToModel(), null, 4));
        const linkElement = document.createElement('a');
        linkElement.setAttribute('href', dataUri);
        linkElement.setAttribute('download',  filename);
        document.body.appendChild(linkElement); // required for firefox
        linkElement.click();
        linkElement.remove();
    }
    
    const leftArmSelected = function() {
        return $(".actions-records").find(".actions-records__arm input[id=princess_arm_left]").prop('checked');
    }
    
    const middleArmSelected = function() {
    	return $(".actions-records").find(".actions-records__arm input[id=princess_arm_middle]").prop('checked');
    }
    
    const rightArmSelected = function() {
        return $(".actions-records").find(".actions-records__arm input[id=princess_arm_right]").prop('checked');
    }
    
    const getAx12IdsToRecord = function() {
        axs = [];
        if (leftArmSelected()) {
            axs = axs.concat([2, 3, 4]);
        }
        if (middleArmSelected()) {
            axs = axs.concat([1, 5, 9]);
        }
        if (rightArmSelected()) {
            axs = axs.concat([6, 7, 8]);
        }
        return axs;
    }
    
    const recordPoolAx12New = function() {
        doajax('post', '/api/record', {
            ax12ids: getAx12IdsToRecord()
        }).then((result) => {
            
            const poolElt = newActionPool("Pool #" + (getPoolItemCount() + 1), true);
            result.forEach((position) => {
                ax12Pos = cloneTemplate("ax12", {
                    ax12Id: position.ax12id,
                    readableAngle: position.readableAngle,
                    rawAngle: position.rawAngle
                });
                poolElt.append(ax12Pos);
            });
            
            addPoolItem(poolElt, true, true);
            
        }).catch(handleAjaxError);
    };
    
    const recordPoolAx12Current = function() {
        let currentPool = getCurrentPlayingItem();
        if (!currentPool) {
            return;
        }
        currentPool = $(currentPool);
        
        doajax('post', '/api/record', {
            ax12ids: getAx12IdsToRecord()
        }).then((result) => {
            
            result.forEach((position) => {
                // Si l'AX12 est déjà présent dans la liste d'actions, on maj sa valeur, sinon on la crée
                let flagFound = false;
                currentPool.find('.action-subitem__ax12').each((index, elt) => {
                    elt = $(elt); 
                    if (elt.find('.action-item__ax12-id').val() == position.ax12id) {
                        elt.find('input').val(position.readableAngle);
                        elt.find('input').data('raw-angle', position.rawAngle);
                        flagFound = true;
                    }
                });
                
                if (!flagFound) {
                    ax12Pos = cloneTemplate("ax12", {
                        ax12Id: position.ax12id,
                        readableAngle: position.readableAngle,
                        rawAngle: position.rawAngle
                    });
                    currentPool.append(ax12Pos);                    
                }
            });
            
        }).then(()=>{}).catch(handleAjaxError);  
    };
    
    const recordPoolPump = function (enable) {
        const poolElt = newActionPool("Pool #" + (getPoolItemCount() + 1), true);
        
        const r = function(side) {
             const pumpItem = cloneTemplate("pump", {
                 pumpId: side,
                 power: enable ? '1' : '0'
             });
            poolElt.append(pumpItem);   
        };
        
        if (leftArmSelected()) {
            r('left');
        }
        
        if (rightArmSelected()) {
            r('right');
        }
        
        addPoolItem(poolElt, true, true);
    }
    
    const recordPoolDelay = function() {
        const delayMs = $(".actions-records .actions-records__delay input").val();
        const poolElt = newActionPool("Pool #" + (getPoolItemCount() + 1), true);
        const delayItem = cloneTemplate("waiting", {
        	delay: delayMs
        });
        
        poolElt.append(delayItem);
        addPoolItem(poolElt, true, true);
    }
    
    const recordBehaviour = function() {
    	const speed = $(".actions-records .actions-records__behaviour .actions-records__behaviour-speed").val();
    	const acceleration = $(".actions-records .actions-records__behaviour .actions-records__behaviour-acceleration").val();
    	const compliance = $(".actions-records .actions-records__behaviour .actions-records__behaviour-compliance").val();
    	const poolElt = newActionPool("Pool #" + (getPoolItemCount() + 1), true);
    	
        getAx12IdsToRecord().forEach((axId) => {
        	const behaviourItem = cloneTemplate("behaviour", {
            	ax12Id: axId,
            	speed: speed,
            	acceleration: acceleration,
            	compliance: compliance
        	});
        	poolElt.append(behaviourItem);
        });
        
        if (poolElt.length > 0) {
        	addPoolItem(poolElt, true, true);	
        }
    }
    
    const recordDisableTorque = function() {
    	const poolElt = newActionPool("Pool #" + (getPoolItemCount() + 1), true);
        getAx12IdsToRecord().forEach((axId) => {
        	const disableTorqueTemplate = cloneTemplate("disableTorque", {
            	ax12Id: axId
        	});
        	poolElt.append(disableTorqueTemplate);
        });
        
        if (poolElt.length > 0) {
        	addPoolItem(poolElt, true, true);	
        }
    }
    
    const loadFileList = function() {
    	doajax('get', '/api/file/list')
    	.then((files) => {
    		const select = $(".actions-records__json .actions-records__json--file-list");
    		select.find("option").remove();
    		files.forEach((file) => {
    			select.append($("<option></option>").attr("value", file.rawName).text(file.name));
    		});
    	}).catch(handleAjaxError);
	}
    
    const loadSelectedJsonFile = function() {
    	const name = $(".actions-records__json .actions-records__json--file-list").val();
    	if (!name) {
    		return;
    	}
    	doajax('post', '/api/file/read', {
    		fileName : name
    	})
    	.then((file) => {
    		loadFromJson(file.content);
    		$(".actions-records__json_filename").val(name);
    	}).catch(handleAjaxError);
    }
    
    const uploadJsonFile = function() {
    	const rawFileName = $(".actions-records__json .actions-records__json_filename").val();
    	const json = encodeActionPoolsToModel();

    	doajax('post', '/api/file/save', {
    		fileName: rawFileName,
    		content: json
    	}).then(() => {
    		afficherMessage("Upload ok");    		
    	}).catch(handleAjaxError);
    }
    
    const releaseAx12 = function () {
        doajax('post', '/api/settings', {
            ax12ids: getAx12IdsToRecord(),
            settings: 'release'
        }).catch(handleAjaxError);
    }
    
    const enablePumps = function(enable) {
        doajax('post', '/api/settings', {
            settings: 'pumps',
            enable: enable
        }).catch(handleAjaxError);
    }
    
    const bindDragNDropArea = function (elt) {
        elt.on('dragenter', function() {
            $(this).css('border', '2px dashed red');
            return false;
        })
        .on('dragover', function(e){
            e.preventDefault();
            e.stopPropagation();
            $(this).css('border', '2px dashed red');
            return false;
        })
        .on('dragleave', function(e) {
            e.preventDefault();
            e.stopPropagation();
            $(this).css('border', '2px dashed #BBBBBB');
            return false;
        })
        .on('drop', function(e) {
            if(e.originalEvent.dataTransfer && e.originalEvent.dataTransfer.files.length){
                e.preventDefault();
                e.stopPropagation();
                var reader = new FileReader();
                const file = e.originalEvent.dataTransfer.files[0]
                reader.onload = function(event) {
                    loadFromRawJson(reader.result);
                    $(".actions-records__json .actions-records__json_filename").val(file.name);
                    $(this).css('border', '2px dashed #00FF00');
                }.bind(this);
                reader.readAsText(file);
            }
            else {
                $(this).css('border', '3px dashed #BBBBBB');
            }
            return false;
        });
    }
    
    /*
     * Câblage GUI
     */
    
    // Application de jQuery UI
    $( "input[type=checkbox]" ).checkboxradio();
    $( "#listeActions" ).sortable();
    $( "#listeActions" ).enableSelection();
    $( "select[data-select]" ).selectmenu();
    $( "input[data-spinner]" ).spinner();
    
    $("#listeActions .actions-item").each((index, elt)=> addToolsElements(elt));
    
    // Création des actions
    $(".actions-records").find(".actions-records__arm .actions-records__arm_new").click(recordPoolAx12New); // Bouton record nouveau
    $(".actions-records").find(".actions-records__arm .actions-records__arm_current").click(recordPoolAx12Current); // Bouton record nouveau
    $(".actions-records").find(".actions-records__pump .ui-icon-power").eq(0).parent().click(() => {recordPoolPump(true)}); // Bouton allumer pompe
    $(".actions-records").find(".actions-records__pump .ui-icon-power").eq(1).parent().click(() => {recordPoolPump(false)}); // Bouton éteindre pompe
    $(".actions-records").find(".actions-records__delay button").click(recordPoolDelay); // Bouton délais
    $(".actions-records").find(".actions-records__behaviour-record").click(recordBehaviour);// Vitesse / accélération / précision
    $(".actions-records").find(".actions-records__behaviour-disable-torque").click(recordDisableTorque); // Désactivation du torque
    // Paramètres globaux
    $(".actions-records").find(".actions-records__factors .actions-records__factors-release").click(releaseAx12); // Relâcher les AX
    $(".actions-records").find(".actions-records__factors .actions-records__factors-pump-on").click(() => enablePumps(true)); // Activer les pompes
    $(".actions-records").find(".actions-records__factors .actions-records__factors-pump-off").click(() => enablePumps(false)); // Eteindre les pompes
    $(".actions-records").find(".actions-records__json .ui-icon-arrowthickstop-1-s").parent().click(downloadActionPools); // Télécharger le Json
    bindDragNDropArea($(".actions-records").find(".actions-records__json .actions-records__json--drop-area")) // Charger un fichier Json par drag'n'drop
    loadFileList(); // Chargement de la liste des fichiers JSon
    $(".actions-records").find(".actions-records__json .ui-icon-refresh").parent().click(loadFileList);
    $(".actions-records").find(".actions-records__json .ui-icon-arrowthick-1-e").parent().click(loadSelectedJsonFile);
    $(".actions-records").find(".actions-records__json .ui-icon-arrowthickstop-1-n").parent().click(uploadJsonFile);
    
    // Rejeux des actions
    $(".actions-controls .ui-icon-arrowrefresh-1-w").parent().click(playFirstItem);
    $(".actions-controls .ui-icon-play").parent().click(playAllFromCurrent);
    $(".actions-controls .ui-icon-seek-start").parent().click(playPreviousItem);
    $(".actions-controls .ui-icon-seek-end").parent().click(playNextItem);
    $(".actions-controls .ui-icon-stop").parent().click(stopPlaying);
    
    // Gestion du Json
    $("#btn_parser").click(() => loadFromRawJson($("#jsonInput").val()));
    $(".actions-records__json .actions-records__json_filename").val('empty.json');
    
    // Tout le JS s'est bien exécuté ? Retrait du message qui dit que non
    $(".js-load-checker").css("display", "none");
} );