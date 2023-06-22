package models;

import AGVDigitalTwinSettings.AGVSettings;
import org.apache.commons.lang3.SerializationUtils;
import system.Positioning;

import java.math.BigDecimal;
import java.util.List;

public class Helper {

    /**
     * Convert Long to BigDecimal
     * @param n Long
     * @return BIgDecimal
     */
    public static BigDecimal longToBigDecimal(Long n){
        return new BigDecimal(n);
    }

    /**
     * Convert Percentage of Long to Decimal
     * @param percentage Long
     * @param n BigDecimal
     * @return BigDecimal
     */
    public static BigDecimal longPercentageToBigDecimalOf(Long percentage, BigDecimal n){
        return Helper.longToBigDecimal(percentage).divide(Helper.longToBigDecimal(100L)).multiply(n);
    }

    /**
     * Get direction from two points in worldmap
     * @param start PositionDTO start position
     * @param end PositionDTO destination position
     * @return Direction enum
     */
    public static Direction getDirection(PositionDTO start, PositionDTO end){
        if (start.wSquare.equals(end.wSquare)) {
            if (start.lSquare.compareTo(end.lSquare) < 0)
                return Direction.L_UP;
            else
                return Direction.L_DOWN;
        }else{
            if (start.wSquare.compareTo(end.wSquare) < 0)
                return Direction.W_UP;
            else
                return Direction.W_DOWN;
        }
    }

    /**
     * Get list of PositionDTO in front and to the sides, given current position and direction
     * example if direction is L_UP
     *  +--+--+
     *  |  |  |
     *  +--+--+--+
     * |agv|  |  |
     *  +--+--+--+
     *  |  |  |
     *  +--+--+
     * 
     * @param currentPosition PositionDTO
     * @param direction Direction to next position
     * @return List<PositionDTO> 
     */
    public static List<PositionDTO> getCloseSquares(PositionDTO currentPosition, Direction direction) {
        var front = new PositionDTO();
        front.lSquare = currentPosition.lSquare;
        front.wSquare = currentPosition.wSquare;
        var twoFront = SerializationUtils.clone(front);
        var right = SerializationUtils.clone(front);
        var left = SerializationUtils.clone(front);

        switch (direction){
            case L_DOWN:
                //one step left
                front.lSquare--;
                twoFront.lSquare--;
                right.lSquare--;
                left.lSquare--;

                twoFront.lSquare--;
                right.wSquare--;
                left.wSquare++;
                break;
            case L_UP:
                //one step left
                front.lSquare++;
                twoFront.lSquare++;
                right.lSquare++;
                left.lSquare++;

                twoFront.lSquare++;
                right.wSquare++;
                left.wSquare--;
                break;
            case W_UP:
                //one step left
                front.wSquare++;
                twoFront.wSquare++;
                right.wSquare++;
                left.wSquare++;

                twoFront.wSquare++;
                right.lSquare--;
                left.lSquare++;
                break;
            case W_DOWN:
                //one step left
                front.wSquare--;
                twoFront.wSquare--;
                right.wSquare--;
                left.wSquare--;

                twoFront.wSquare--;
                right.lSquare++;
                left.lSquare--;
                break;
            default:
                break;
        }

        return List.of(front,twoFront,left,right);
    }

    /**
     * Get right position to avoid obstacle, returns current position if right not found
     * @param currentPosition PositionDTO
     * @param direction Direction
     * @return PositionDTO
     */
    public static PositionDTO getRight(PositionDTO currentPosition, Direction direction){
        switch (direction){
            case L_DOWN:
                currentPosition.wSquare--;
                break;
            case L_UP:
                currentPosition.wSquare++;
                break;
            case W_UP:
                currentPosition.lSquare--;
                break;
            case W_DOWN:
                currentPosition.lSquare++;
                break;
            default:
                break;
        }
        return currentPosition;
    }

    /**
     * Wait for lock lift
     */
    public static void waitForEvent(Object lock){
        synchronized (lock){
            boolean ended = false;
            while (!ended){
                try {
                    lock.wait();
                    ended = true;
                }catch (InterruptedException e){
                    ended = false;
                }
            }
        }
    }
    public static void raiseEvent(Object lock){
        synchronized (lock){
            lock.notify();
        }
    }

    /**
     * Verify if position is valid in worldmap, checks if not out of bounds and if not an obstacle
     * @param world WarehouseDTO
     * @param position PositionDTO
     * @return boolean
     */
    public static boolean isPositionValid(WarehouseDTO world, PositionDTO position){
        return  !(
                    position.lSquare > world.length  || position.lSquare < 1 //L Square between 1 and max
                    || position.wSquare > world.width  || position.wSquare < 1 //W Square between 1 and max
                    || world.aisles.stream().anyMatch(
                        aisleDTO -> aisleDTO.rows.stream().anyMatch(
                                        rowDTO -> rowDTO.squaresOcupied.stream().anyMatch(
                                                positionDTO -> positionDTO.equals(position)
                                        )
                        )
                    ) //Find if is part of any aisle
                    || world.docks.stream().anyMatch(dockDTO -> dockDTO.position.equals(position)) //Find if is any dock
                );
    }

    /**
     * Get digital twin status data
     * @param info SharedInformation
     * @return AgvDataResponse
     */
    public static AgvDataResponse getDigitalTwinStatus(SharedInformation info){
        var agvData = new AgvDataResponse();
        agvData.id = AGVSettings.Settings.ID;
        agvData.status = info.currentStatus();
        agvData.currentPosition = info.currentPosition();
        agvData.currentBattery = info.battery().current;
        return agvData;
    }
}
