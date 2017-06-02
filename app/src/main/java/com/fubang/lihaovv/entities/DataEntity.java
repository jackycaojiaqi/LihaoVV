package com.fubang.lihaovv.entities;

/**
 * Created by dell on 2016/4/5.
 */
public class DataEntity {
        private String id;
        private String position;
        private String chnId;
        private String roomId;
        private String matchId;
        private String gameId;
        private String spic;
        private String bpic;
        private String title;
        private String url;
        private String contents;
        private String startTime;
        private String endTime;
        private String weight;
        private String positionType;

        private RoomZhanqiEntity room;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getChnId() {
            return chnId;
        }

        public void setChnId(String chnId) {
            this.chnId = chnId;
        }

        public String getRoomId() {
            return roomId;
        }

        public void setRoomId(String roomId) {
            this.roomId = roomId;
        }

        public String getMatchId() {
            return matchId;
        }

        public void setMatchId(String matchId) {
            this.matchId = matchId;
        }

        public String getGameId() {
            return gameId;
        }

        public void setGameId(String gameId) {
            this.gameId = gameId;
        }

        public String getSpic() {
            return spic;
        }

        public void setSpic(String spic) {
            this.spic = spic;
        }

        public String getBpic() {
            return bpic;
        }

        public void setBpic(String bpic) {
            this.bpic = bpic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getContents() {
            return contents;
        }

        public void setContents(String contents) {
            this.contents = contents;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getPositionType() {
            return positionType;
        }

        public void setPositionType(String positionType) {
            this.positionType = positionType;
        }

        public RoomZhanqiEntity getRoom() {
            return room;
        }

        public void setRoom(RoomZhanqiEntity room) {
            this.room = room;
        }



}
